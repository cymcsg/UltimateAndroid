/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.standUpTimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.*;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.standuptimer.model.Meeting;
import com.marshalchen.common.uimodule.standuptimer.model.MeetingStats;
import com.marshalchen.common.uimodule.standuptimer.model.Team;
import com.marshalchen.common.uimodule.standuptimer.utils.Logger;
import com.marshalchen.common.uimodule.standuptimer.utils.TimeFormatHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamDetails extends TabActivity {
    private static final int CONFIRM_DELETE_MEETING_DIALOG = 1;
    private static final int CONFIRM_DELETE_TEAM_DIALOG = 2;

    private Team team = null;
    private ListView meetingList = null;
    private Dialog confirmDeleteMeetingDialog = null;
    private Dialog confirmDeleteTeamDialog = null;
    private Integer positionOfMeetingToDelete = null;
    private ArrayAdapter<String> meetingListAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_up_timer_team_details);

        team = Team.findByName(getIntent().getStringExtra("teamName"), this);
        meetingList = new ListView(this);
        meetingList.setOnItemClickListener(createMeetingListClickListener());

        registerForContextMenu(meetingList);
        createTabs();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTabContents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stand_up_timer_team_details_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.delete_team_from_details:
            Logger.d("Displaying the delete team dialog box");
            showDialog(CONFIRM_DELETE_TEAM_DIALOG);
            return true;
        default:
            Logger.e("Unknown menu item selected");
            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stand_up_timer_meetings_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
        case R.id.delete_meeting:
            positionOfMeetingToDelete = info.position;
            showDialog(CONFIRM_DELETE_MEETING_DIALOG);
            return true;
        default:
            return super.onContextItemSelected(item);
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

        case CONFIRM_DELETE_TEAM_DIALOG:
            if (confirmDeleteTeamDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete this team?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", deleteTeamConfirmationListener());
                builder.setNegativeButton("No", cancelListener());
                confirmDeleteTeamDialog = builder.create();
            }
            return confirmDeleteTeamDialog;

        default:
            Logger.e("Attempting to create an unkonwn dialog with an id of " + id);
            return null;
        }
    }

    private OnItemClickListener createMeetingListClickListener() {
        return new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String meetingTime = meetingListAdapter.getItem(position);

                Intent intent = new Intent(TeamDetails.this, MeetingDetails.class);
                intent.putExtra("teamName", team.getName());
                intent.putExtra("meetingTime", meetingTime);
                startActivity(intent);
            }
        };
    }

    private void createTabs() {
        TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("stats_tab").
                setIndicator("stats").
                setContent(createMeetingDetails(team)));

        tabHost.addTab(tabHost.newTabSpec("meetings_tab").
                setIndicator("meetings").
                setContent(createMeetingList()));

        getTabHost().setCurrentTab(0);
        findViewById(R.id.team_stats).bringToFront();
    }

    private TabHost.TabContentFactory createMeetingList() {
        return new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                meetingListAdapter = createMeetingListAdapter();
                meetingList.setAdapter(meetingListAdapter);
                return meetingList;
            }
        };
    }

    private TabHost.TabContentFactory createMeetingDetails(final Team team) {
        return new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                setStatsTabContent();
                return findViewById(R.id.team_stats);
            }
        };
    }

    protected DialogInterface.OnClickListener deleteMeetingConfirmationListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String dateString = (String) meetingList.getAdapter().getItem(positionOfMeetingToDelete);
                deleteMeeting(dateString);
                updateTabContents();
            }
        };
    }

    protected DialogInterface.OnClickListener deleteTeamConfirmationListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                team.delete(TeamDetails.this);
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

    private void deleteMeeting(String dateString) {
        try {
            Date date = new SimpleDateFormat(Meeting.DESCRIPTION_FORMAT).parse(dateString);
            Meeting meeting = Meeting.findByTeamAndDate(team, date, this);
            meeting.delete(this);
        } catch (ParseException e) {
            Logger.e(e.getMessage());
            Logger.e("Could not parse the date string '" + dateString + "'.  Will not attempt to delete the meeting.");
        }
    }

    private void updateTabContents() {
        setStatsTabContent();
        meetingListAdapter = createMeetingListAdapter();
        meetingList.setAdapter(meetingListAdapter);
    }

    private void setStatsTabContent() {
        if (team != null && team.hasMeetings(this)) {
            MeetingStats stats = team.getAverageMeetingStats(TeamDetails.this);
            ((TextView) findViewById(R.id.meeting_team_name_label)).setText("team_name");
            ((TextView) findViewById(R.id.meeting_team_name)).setText(team.getName());

            ((TextView) findViewById(R.id.number_of_meetings_label)).setText("number_of_meetings");
            ((TextView) findViewById(R.id.number_of_meetings)).setText(Integer.toString((int) team.getNumberOfMeetings(TeamDetails.this)));

            ((TextView) findViewById(R.id.avg_number_of_participants_label)).setText("avg_number_of_participants");
            ((TextView) findViewById(R.id.avg_number_of_participants)).setText(Float.toString(stats.getNumParticipants()));

            ((TextView) findViewById(R.id.avg_meeting_length_label)).setText("avg_meeting_length");
            ((TextView) findViewById(R.id.avg_meeting_length)).setText(TimeFormatHelper.formatTime(stats.getMeetingLength()));

            ((TextView) findViewById(R.id.avg_individual_status_length_label)).setText("avg_individual_status_length");
            ((TextView) findViewById(R.id.avg_individual_status_length)).setText(TimeFormatHelper.formatTime(stats.getIndividualStatusLength()));

            ((TextView) findViewById(R.id.avg_quickest_status_label)).setText("avg_quickest_status");
            ((TextView) findViewById(R.id.avg_quickest_status)).setText(TimeFormatHelper.formatTime(stats.getQuickestStatus()));

            ((TextView) findViewById(R.id.avg_longest_status_label)).setText("avg_longest_status");
            ((TextView) findViewById(R.id.avg_longest_status)).setText(TimeFormatHelper.formatTime(stats.getLongestStatus()));
        } else {
            ((TextView) findViewById(R.id.meeting_team_name_label)).setText("no_meeting_stats");
            ((TextView) findViewById(R.id.meeting_team_name)).setText("");

            ((TextView) findViewById(R.id.number_of_meetings_label)).setText("");
            ((TextView) findViewById(R.id.number_of_meetings)).setText("");

            ((TextView) findViewById(R.id.avg_number_of_participants_label)).setText("");
            ((TextView) findViewById(R.id.avg_number_of_participants)).setText("");

            ((TextView) findViewById(R.id.avg_meeting_length_label)).setText("");
            ((TextView) findViewById(R.id.avg_meeting_length)).setText("");

            ((TextView) findViewById(R.id.avg_individual_status_length_label)).setText("");
            ((TextView) findViewById(R.id.avg_individual_status_length)).setText("");

            ((TextView) findViewById(R.id.avg_quickest_status_label)).setText("");
            ((TextView) findViewById(R.id.avg_quickest_status)).setText("");

            ((TextView) findViewById(R.id.avg_longest_status_label)).setText("");
            ((TextView) findViewById(R.id.avg_longest_status)).setText("");
        }
    }

    private ArrayAdapter<String> createMeetingListAdapter() {
        List<String> meetingDescriptions = new ArrayList<String>();
        if (team != null) {
            List<Meeting> meetings = team.findAllMeetings(TeamDetails.this);
            for (Meeting meeting : meetings) {
                meetingDescriptions.add(meeting.getDescription());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TeamDetails.this,
                android.R.layout.simple_list_item_1, meetingDescriptions);
        return adapter;
    }

    public AlertDialog getConfirmDeleteTeamDialog() {
        return (AlertDialog) confirmDeleteTeamDialog;
    }
}
