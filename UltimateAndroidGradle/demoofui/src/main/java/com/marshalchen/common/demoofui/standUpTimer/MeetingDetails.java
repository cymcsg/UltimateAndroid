/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.standUpTimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.standuptimer.model.Meeting;
import com.marshalchen.common.uimodule.standuptimer.model.MeetingStats;
import com.marshalchen.common.uimodule.standuptimer.model.Team;
import com.marshalchen.common.uimodule.standuptimer.utils.Logger;
import com.marshalchen.common.uimodule.standuptimer.utils.TimeFormatHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingDetails extends Activity {
    private static final int CONFIRM_DELETE_MEETING_DIALOG = 1;

    private Dialog confirmDeleteMeetingDialog = null;
    private Meeting meeting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_up_timer_meeting_details);
    
        String teamName = getIntent().getStringExtra("teamName");
        String meetingTime = getIntent().getStringExtra("meetingTime");

        Logger.i("Looking for meeting for '" + teamName + "' at '" + meetingTime + "'");

        Date date = null;
        try {
            date = new SimpleDateFormat(Meeting.DESCRIPTION_FORMAT).parse(meetingTime);
        } catch (ParseException e) {
            String msg = "Could not parse the date/time '" + meetingTime + "'. " + e.getMessage();
            Logger.e(msg);
            throw new RuntimeException(msg);
        }

        Team team = Team.findByName(teamName, this);
        meeting = Meeting.findByTeamAndDate(team, date, this);
        displayMeetingStats(team, date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stand_up_timer_meeting_details_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.delete_meeting_from_details:
            Logger.d("Displaying the delete meeting dialog box");
            showDialog(CONFIRM_DELETE_MEETING_DIALOG);
            return true;
        default:
            Logger.e("Unknown menu item selected");
            return false;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case CONFIRM_DELETE_MEETING_DIALOG:
            if (confirmDeleteMeetingDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete this meeting?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", deleteMeetingConfirmationListener());
                builder.setNegativeButton("No", cancelListener());
                confirmDeleteMeetingDialog = builder.create();
            }
            return confirmDeleteMeetingDialog;

        default:
            Logger.e("Attempting to create an unkonwn dialog with an id of " + id);
            return null;
        }
    }

    protected DialogInterface.OnClickListener deleteMeetingConfirmationListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                meeting.delete(MeetingDetails.this);
                finish();
            }
        };
    }

    protected DialogInterface.OnClickListener cancelListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        };
    }

    private void displayMeetingStats(Team team, Date date) {
        MeetingStats stats = meeting.getMeetingStats();

        ((TextView) findViewById(R.id.meeting_details_team_name)).setText(team.getName());
        ((TextView) findViewById(R.id.meeting_time)).setText(meeting.getDescription());

        ((TextView) findViewById(R.id.number_of_participants_label)).setText("number_of_participants");
        ((TextView) findViewById(R.id.number_of_participants)).setText(Integer.toString((int) stats.getNumParticipants()));

        ((TextView) findViewById(R.id.meeting_length_label)).setText("meeting_length");
        ((TextView) findViewById(R.id.meeting_length)).setText(TimeFormatHelper.formatTime(stats.getMeetingLength()));

        ((TextView) findViewById(R.id.individual_status_length_label)).setText("individual_status_length");
        ((TextView) findViewById(R.id.individual_status_length)).setText(TimeFormatHelper.formatTime(stats.getIndividualStatusLength()));

        ((TextView) findViewById(R.id.quickest_status_label)).setText("quickest_status");
        ((TextView) findViewById(R.id.quickest_status)).setText(TimeFormatHelper.formatTime(stats.getQuickestStatus()));

        ((TextView) findViewById(R.id.longest_status_label)).setText("longest_status");
        ((TextView) findViewById(R.id.longest_status)).setText(TimeFormatHelper.formatTime(stats.getLongestStatus()));
    }

    public AlertDialog getConfirmDeleteMeetingDialog() {
        return (AlertDialog) confirmDeleteMeetingDialog;
    }
}
