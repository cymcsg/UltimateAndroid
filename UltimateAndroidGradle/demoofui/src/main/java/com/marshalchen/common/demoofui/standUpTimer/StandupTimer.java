/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.standUpTimer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.standuptimer.model.Meeting;
import com.marshalchen.common.uimodule.standuptimer.model.Team;
import com.marshalchen.common.uimodule.standuptimer.utils.Logger;
import com.marshalchen.common.uimodule.standuptimer.utils.TimeFormatHelper;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class StandupTimer extends Activity implements OnClickListener {
    protected static final String REMAINING_INDIVIDUAL_SECONDS = "remainingIndividualSeconds";
    protected static final String REMAINING_MEETING_SECONDS = "remainingMeetingSeconds";
    protected static final String STARTING_INDIVIDUAL_SECONDS = "startingIndividualSeconds";
    protected static final String COMPLETED_PARTICIPANTS = "completedParticipants";
    protected static final String TOTAL_PARTICIPANTS = "totalParticipants";
    protected static final String CURRENT_INDIVIDUAL_STATUS_SECONDS = "currentIndividualStatusSeconds";
    protected static final String MEETING_START_TIME = "meetingStartTime";
    protected static final String INDIVIDUAL_STATUS_END_TIME = "individualStatusEndTime";
    protected static final String QUICKEST_STATUS = "quickestStatus";
    protected static final String LONGEST_STATUS = "longestStatus";

    private int currentIndividualStatusSeconds = 0;
    private int remainingIndividualSeconds = 0;
    private int remainingMeetingSeconds = 0;
    private int startingIndividualSeconds = 0;
    private int completedParticipants = 0;
    private int totalParticipants = 0;
    private int warningTime = 0;

    private boolean finished = false;
    private Timer timer = null;
    private PowerManager.WakeLock wakeLock = null;

    private Team team = null;
    private long meetingStartTime = 0;
    private long individualStatusStartTime = 0;
    private long individualStatusEndTime = 0;
    private int quickestStatus = Integer.MAX_VALUE;
    private int longestStatus = 0;

    private static MediaPlayer bell = null;
    private static MediaPlayer airhorn = null;

    private Handler updateDisplayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateDisplay();
        }
    };

    private Handler disableIndividualTimerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            disableIndividualTimer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_up_timer_timer);

        team = Team.findByName(getIntent().getStringExtra("teamName"), this);
        if (team != null) {
            Logger.i("Starting new meeting for team '" + team.getName() + "'");
        }

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        initializeSounds();
        initializeButtonListeners();
        initializeTimerValues();
        updateDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        acquireWakeLock();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        synchronized(this) {
            cancelTimer();
            releaseWakeLock();

            if (finished) {
                clearState();
            } else {
                saveState();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        Logger.i("Key pressed: " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Shutdown the timer when back is pressed.  Let the framework
            // handle the back behavior.
            shutdownTimer();
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.next_button:
            processNextButtonClick();
            break;

        case R.id.finished_button:
            processFinishedButtonClick();
            break;
        }
    }

    private void initializeSounds() {
        if (bell == null) {
            Logger.d("Loading the bell sound");
            bell = MediaPlayer.create(this, R.raw.bell);
        }

        if (airhorn == null) {
            Logger.d("Loading the airhorn sound");
            airhorn = MediaPlayer.create(this, R.raw.airhorn);
        }
    }

    private void initializeButtonListeners() {
        View nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);

        View finishedButton = findViewById(R.id.finished_button);
        finishedButton.setOnClickListener(this);
    }

    private void initializeTimerValues() {
        int meetingLength = getIntent().getIntExtra("meetingLength", 0);
        int numParticipants = getIntent().getIntExtra("numParticipants", 0);

        Logger.d("Data from Intent: meetingLength = " + meetingLength);
        Logger.d("Data from Intent: numParticipants = " + numParticipants);

        loadState(meetingLength, numParticipants);
    }

    protected synchronized void updateDisplay() {
        if (individualStatusInProgress()) {
            TextView individualTimeRemaining = (TextView) findViewById(R.id.individual_time_remaining);
            individualTimeRemaining.setText(TimeFormatHelper.formatTime(remainingIndividualSeconds));
            individualTimeRemaining.setTextColor(TimeFormatHelper.determineColor(remainingIndividualSeconds, warningTime));

            TextView participantNumber = (TextView) findViewById(R.id.participant_number);
            participantNumber.setText("participant" + " " +
                    (completedParticipants + 1) + "/" + totalParticipants);
        } else {
            disableIndividualTimer();
        }

        TextView totalTimeRemaining = (TextView) findViewById(R.id.total_time_remaining);
        totalTimeRemaining.setText(TimeFormatHelper.formatTime(remainingMeetingSeconds));
        totalTimeRemaining.setTextColor(TimeFormatHelper.determineColor(remainingMeetingSeconds, warningTime));
    }

    private synchronized void startTimer() {
        Logger.d("Starting a new timer");

        timer = new Timer();
        TimerTask updateTimerValuesTask = new TimerTask() {
            @Override
            public void run() {
                updateTimerValues();
            }
        };
        timer.schedule(updateTimerValuesTask, 1000, 1000);
    }

    private synchronized void cancelTimer() {
        if (timer != null) {
            Logger.d("Canceling timer");
            timer.cancel();
            timer = null;
        }
    }

    protected synchronized void updateTimerValues() {
        currentIndividualStatusSeconds++;

        if (remainingIndividualSeconds > 0) {
            remainingIndividualSeconds--;

            if (remainingIndividualSeconds == warningTime) {
                Logger.d("Playing the bell sound");
                if (Prefs.playSounds(this)) {
                    playWarningSound();
                }
            } else if (remainingIndividualSeconds == 0) {
                Logger.d("Playing the airhorn sound");
                if (Prefs.playSounds(this)) {
                    playFinishedSound();
                }
            }
        }

        if (remainingMeetingSeconds > 0)
            remainingMeetingSeconds--;

        updateDisplayHandler.sendEmptyMessage(0);
    }

    private synchronized void processNextButtonClick() {
        completedParticipants++;
        calculateIndividualStatusStats();

        if (completedParticipants == totalParticipants) {
            Logger.d("Individual status complete");
            individualStatusEndTime = System.currentTimeMillis();
            disableIndividualTimerHandler.sendEmptyMessage(0);
        } else {
            Logger.d("Setting up the next participant");
            if (startingIndividualSeconds < remainingMeetingSeconds) {
                remainingIndividualSeconds = startingIndividualSeconds;
            } else {
                remainingIndividualSeconds = remainingMeetingSeconds;
            }
            updateDisplay();
        }
    }

    private synchronized void calculateIndividualStatusStats() {
        if (currentIndividualStatusSeconds > longestStatus) {
            longestStatus = currentIndividualStatusSeconds;
        }

        if (currentIndividualStatusSeconds < quickestStatus) {
            quickestStatus = currentIndividualStatusSeconds;
        }

        currentIndividualStatusSeconds = 0;
    }

    protected synchronized void disableIndividualTimer() {
        Logger.d("Disabling the individual timer");

        remainingIndividualSeconds = 0;

        TextView participantNumber = (TextView) findViewById(R.id.participant_number);
        participantNumber.setText("individual_status_complete");

        TextView individualTimeRemaining = (TextView) findViewById(R.id.individual_time_remaining);
        individualTimeRemaining.setText(TimeFormatHelper.formatTime(remainingIndividualSeconds));
        individualTimeRemaining.setTextColor(Color.GRAY);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setClickable(false);
        nextButton.setTextColor(Color.GRAY);
    }

    private void acquireWakeLock() {
        if (wakeLock == null) {
            Logger.d("Acquiring wake lock");
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, this.getClass().getCanonicalName());
            wakeLock.acquire();
        }
    }

    private void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            Logger.d("Releasing wake lock");
            wakeLock.release();
            wakeLock = null;
        }
    }

    protected synchronized void loadState(int meetingLength, int numParticipants) {
        warningTime = Prefs.getWarningTime(this);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        totalParticipants = preferences.getInt(TOTAL_PARTICIPANTS, numParticipants);
        remainingMeetingSeconds = preferences.getInt(REMAINING_MEETING_SECONDS, (meetingLength * 60));
        startingIndividualSeconds = preferences.getInt(STARTING_INDIVIDUAL_SECONDS, remainingMeetingSeconds / totalParticipants);
        remainingIndividualSeconds = preferences.getInt(REMAINING_INDIVIDUAL_SECONDS, startingIndividualSeconds);
        completedParticipants = preferences.getInt(COMPLETED_PARTICIPANTS, 0);
        currentIndividualStatusSeconds = preferences.getInt(CURRENT_INDIVIDUAL_STATUS_SECONDS, 0);
        meetingStartTime = preferences.getLong(MEETING_START_TIME, System.currentTimeMillis());
        individualStatusEndTime = preferences.getLong(INDIVIDUAL_STATUS_END_TIME, 0);
        quickestStatus = preferences.getInt(QUICKEST_STATUS, Integer.MAX_VALUE);
        longestStatus = preferences.getInt(LONGEST_STATUS, 0);

        team = Team.findByName(getIntent().getStringExtra("teamName"), this);
        individualStatusStartTime = meetingStartTime;
    }

    private synchronized void saveState() {
        SharedPreferences.Editor preferences = getPreferences(MODE_PRIVATE).edit();
        preferences.putInt(REMAINING_INDIVIDUAL_SECONDS, remainingIndividualSeconds);
        preferences.putInt(REMAINING_MEETING_SECONDS, remainingMeetingSeconds);
        preferences.putInt(STARTING_INDIVIDUAL_SECONDS, startingIndividualSeconds);
        preferences.putInt(COMPLETED_PARTICIPANTS, completedParticipants);
        preferences.putInt(TOTAL_PARTICIPANTS, totalParticipants);
        preferences.putInt(CURRENT_INDIVIDUAL_STATUS_SECONDS, currentIndividualStatusSeconds);
        preferences.putLong(MEETING_START_TIME, meetingStartTime);
        preferences.putLong(INDIVIDUAL_STATUS_END_TIME, individualStatusEndTime);
        preferences.putInt(QUICKEST_STATUS, quickestStatus);
        preferences.putInt(LONGEST_STATUS, longestStatus);
        preferences.commit();
    }

    private void clearState() {
        getPreferences(MODE_PRIVATE).edit().clear().commit();
    }

    private synchronized boolean individualStatusInProgress() {
        return completedParticipants < totalParticipants;
    }

    private synchronized void processFinishedButtonClick() {
        if (completedParticipants != totalParticipants) {
            completedParticipants++;
            calculateIndividualStatusStats();
        }

        shutdownTimer();
        storeMeetingStats();
        finish();
    }

    private synchronized void shutdownTimer() {
        destroySounds();
        finished = true;
    }

    private static void destroySounds() {
        bell.stop();
        bell.release();
        bell = null;

        airhorn.stop();
        airhorn.release();
        airhorn = null;
    }

    protected void playWarningSound() {
        playSound(bell);
    }

    protected void playFinishedSound() {
        playSound(airhorn);
    }

    private void playSound(MediaPlayer mp) {
        mp.seekTo(0);
        mp.start();
    }

    private void storeMeetingStats() {
        if (team != null) {
            long meetingEndTime = System.currentTimeMillis();
            if (individualStatusEndTime == 0) {
                individualStatusEndTime = meetingEndTime;
            }

            try {
                Meeting meeting = new Meeting(team, new Date(meetingStartTime), completedParticipants,
                        (int)((individualStatusEndTime - individualStatusStartTime) / 1000),
                        (int)((meetingEndTime - meetingStartTime) / 1000),
                        quickestStatus, longestStatus);
                persistMeeting(meeting);
            } catch (IllegalArgumentException e) {
                Logger.e("Could not store the meeting in the database.  " + e);
            }
        }
    }

    protected void persistMeeting(Meeting meeting) {
        meeting.save(this);
    }

    protected synchronized int getRemainingIndividualSeconds() {
        return remainingIndividualSeconds;
    }

    protected synchronized int getRemainingMeetingSeconds() {
        return remainingMeetingSeconds;
    }

    protected synchronized int getStartingIndividualSeconds() {
        return startingIndividualSeconds;
    }

    protected synchronized int getCompletedParticipants() {
        return completedParticipants;
    }

    protected synchronized int getTotalParticipants() {
        return totalParticipants;
    }

    protected synchronized int getWarningTime() {
        return warningTime;
    }

    protected synchronized boolean isFinished() {
        return finished;
    }

    protected synchronized Timer getTimer() {
        return timer;
    }

    protected synchronized PowerManager.WakeLock getWakeLock() {
        return wakeLock;
    }

    protected synchronized Team getTeam() {
        return team;
    }

    protected synchronized void setTeam(Team team) {
        this.team = team;
    }

    protected synchronized long getMeetingStartTime() {
        return meetingStartTime;
    }

    protected synchronized long getIndividualStatusStartTime() {
        return individualStatusStartTime;
    }

    protected synchronized long getIndividualStatusEndTime() {
        return individualStatusEndTime;
    }

    protected synchronized int getQuickestStatus() {
        return quickestStatus;
    }

    protected synchronized int getLongestStatus() {
        return longestStatus;
    }

    protected synchronized int getCurrentIndividualStatusSeconds() {
        return currentIndividualStatusSeconds;
    }
}
