/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.standUpTimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.standuptimer.model.Team;
import com.marshalchen.common.uimodule.standuptimer.utils.Logger;

public class ConfigureStandupTimer extends Activity implements OnClickListener {
    private static final String MEETING_LENGTH = "meetingLength";
    private static final String NUMBER_OF_PARTICIPANTS = "numberOfParticipants";
    private static final String TEAM_NAMES_POS = "teamNamesPos";
    private static final int MAX_ALLOWED_PARTICIPANTS = Integer.MAX_VALUE;
    private static final int MAX_ALLOWED_MEETING_LENGTH = Integer.MAX_VALUE;

    private int meetingLength = 0;
    private int numParticipants = 0;
    private int teamNamesPos = 0;

    private Spinner meetingLengthSpinner = null;
    private EditText meetingLengthEditText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_up_timer_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeGUIElements();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stand_up_timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.about:
            Logger.d("Displaying the about box");
            displayAboutBox();
            return true;
        case R.id.help:
            Logger.d("Displaying the help dialog");
            displayHelpDialog();
            return true;
        case R.id.settings:
            Logger.d("Displaying the settings");
            displaySettings();
            return true;
        case R.id.teams:
            Logger.d("Displaying the team configuration");
            displayTeamConfiguration();
            return true;
        default:
            Logger.e("Unknown menu item selected");
            return false;
        }
    }

    protected void displaySettings() {
        startActivity(new Intent(this, Prefs.class));
    }

    protected void displayAboutBox() {
//        startActivity(new Intent(this, About.class));
    }

    protected void displayHelpDialog() {
        startActivity(new Intent(this, Help.class));
    }

    protected void displayTeamConfiguration() {
        startActivity(new Intent(this, TeamList.class));
    }

    public void onClick(View v) {
        Intent i = new Intent(this, StandupTimer.class);

        meetingLength = getMeetingLengthFromUI();
        i.putExtra("meetingLength", meetingLength);

        TextView t = (TextView) findViewById(R.id.num_participants);
        numParticipants = parseNumberOfParticipants(t);
        i.putExtra("numParticipants", numParticipants);

        Spinner teamNameSpinner = (Spinner) findViewById(R.id.team_names);
        teamNamesPos = teamNameSpinner.getSelectedItemPosition();
        i.putExtra("teamName", (String) teamNameSpinner.getSelectedItem());

        if (numParticipants < 1 || (Prefs.allowUnlimitedParticipants(this) == false && numParticipants > 20)) {
            showInvalidNumberOfParticipantsDialog();
        } else {
            saveState();
            startTimer(i);
        }
    }

    protected void showInvalidNumberOfParticipantsDialog() {
        showDialog(0);
    }

    protected void startTimer(Intent i) {
        startActivity(i);
    }

    private void initializeGUIElements() {
        loadState();
        initializeNumberOfParticipants();
        initializeMeetingLength();
        initializeTeamNamesSpinner();
        initializeStartButton();
    }

    private void initializeNumberOfParticipants() {
        TextView t = (TextView) findViewById(R.id.num_participants);
        t.setText(Integer.toString(numParticipants));
    }

    private void initializeMeetingLength() {
        ViewGroup meetingLengthContainer = (ViewGroup) findViewById(R.id.meeting_length_container);
        meetingLengthContainer.removeAllViews();

        View meetingLengthView = null;
        if (Prefs.allowVariableMeetingLength(this)) {
            meetingLengthView = createMeetingLengthTextBox();
        } else {
            meetingLengthView = createMeetingLengthSpinner();
        }

        meetingLengthContainer.addView(meetingLengthView);
    }

    private View createMeetingLengthTextBox() {
        meetingLengthEditText = new EditText(this);
        meetingLengthEditText.setGravity(Gravity.CENTER);
        meetingLengthEditText.setKeyListener(new DigitsKeyListener());
        meetingLengthEditText.setRawInputType(InputType.TYPE_CLASS_PHONE);
        meetingLengthEditText.setLayoutParams(new LayoutParams(dipsToPixels(60), LayoutParams.WRAP_CONTENT));
        meetingLengthEditText.setText(Integer.toString(meetingLength));
        meetingLengthEditText.setLines(1);

        meetingLengthSpinner = null;
        return meetingLengthEditText;
    }

    private View createMeetingLengthSpinner() {
        meetingLengthSpinner = new Spinner(this);
        meetingLengthSpinner.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        meetingLengthSpinner.setPrompt("length_of_meeting");

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.meeting_lengths,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingLengthSpinner.setAdapter(adapter);
        meetingLengthSpinner.setSelection(getSpinnerPositionFromMeetingLength(meetingLength));

        meetingLengthEditText = null;
        return meetingLengthSpinner;
    }

    private void initializeTeamNamesSpinner() {
        Spinner s = (Spinner) findViewById(R.id.team_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Team.findAllTeamNames(this));
        adapter.add(" [No Team] ");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(teamNamesPos);
    }

    private void initializeStartButton() {
        View startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
    }

    private void saveState() {
        Logger.i("Saving state.  mettingLength = " + meetingLength +
                ", numParticipants = " + numParticipants +
                ", teamNamePos = " + teamNamesPos);
        SharedPreferences.Editor preferences = getPreferences(MODE_PRIVATE).edit();
        preferences.putInt(MEETING_LENGTH, meetingLength);
        preferences.putInt(NUMBER_OF_PARTICIPANTS, numParticipants);
        preferences.putInt(TEAM_NAMES_POS, teamNamesPos);
        preferences.commit();
    }

    protected void loadState() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        meetingLength = preferences.getInt(MEETING_LENGTH, 5);
        numParticipants = preferences.getInt(NUMBER_OF_PARTICIPANTS, 2);
        teamNamesPos = preferences.getInt(TEAM_NAMES_POS, 0);
        Logger.i("Retrieved state.  mettingLengthPos = " + meetingLength +
                ", numParticipants = " + numParticipants +
                ", teamNamePos = " + teamNamesPos);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getWarningMessage())
            .setCancelable(true)
            .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dismissDialog(0);
                }
            });
        return builder.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        ((AlertDialog) dialog).setMessage(this.getString(getWarningMessage()));
    }

    private int getWarningMessage() {
        if (Prefs.allowUnlimitedParticipants(this)) {
            return R.string.valid_num_participants_warning_unlimited;
        } else {
            return R.string.valid_num_participants_warning;
        }
    }

    private int parseNumberOfParticipants(TextView t) {
        int numberOfParticipants = numParticipants;

        try {
            numberOfParticipants = Integer.parseInt(t.getText().toString());
        } catch (NumberFormatException e) {
            Logger.w("Invalid number of participants provided.  Defaulting to previous value.");
        }

        if (numberOfParticipants > MAX_ALLOWED_PARTICIPANTS) {
            return MAX_ALLOWED_PARTICIPANTS;
        } else {
            return numberOfParticipants;
        }
    }

    private int getMeetingLengthFromUI() {
        if (meetingLengthEditText != null) {
            return getMeetingLengthFromTextArea();
        } else {
            return getMeetingLengthFromSpinner();
        }
    }

    private int getMeetingLengthFromTextArea() {
        int length = meetingLength;

        try {
            length = Integer.parseInt(meetingLengthEditText.getText().toString());
        } catch (NumberFormatException e) {
            Logger.w("Invalid meeting length provided.  Defaulting to previous value.");
        }

        if (length > MAX_ALLOWED_MEETING_LENGTH) {
            return MAX_ALLOWED_MEETING_LENGTH;
        } else {
            return length;
        }
    }

    private int getMeetingLengthFromSpinner() {
        int minutes = 0;
        switch (meetingLengthSpinner.getSelectedItemPosition()) {
            case 0: minutes = 5;  break;
            case 1: minutes = 10; break;
            case 2: minutes = 15; break;
            case 3: minutes = 20; break;
        }
        return minutes;
    }

    private int getSpinnerPositionFromMeetingLength(int meetingLength) {
        int pos = 0;
        switch (meetingLength) {
            case 5:  pos = 0; break;
            case 10: pos = 1; break;
            case 15: pos = 2; break;
            case 20: pos = 3; break;
        }
        return pos;
    }

    private int dipsToPixels(int dips) {
        return (int) (dips * getResources().getDisplayMetrics().density);
    }

    protected int getMeetingLength() {
        return meetingLength;
    }

    protected int getNumParticipants() {
        return numParticipants;
    }

    protected int getTeamNamesPos() {
        return teamNamesPos;
    }

    protected Spinner getMeetingLengthSpinner() {
        return meetingLengthSpinner;
    }

    protected EditText getMeetingLengthEditText() {
        return meetingLengthEditText;
    }
}
