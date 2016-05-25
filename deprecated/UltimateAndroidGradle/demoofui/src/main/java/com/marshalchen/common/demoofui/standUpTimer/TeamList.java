/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.standUpTimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.standuptimer.model.Team;
import com.marshalchen.common.uimodule.standuptimer.utils.Logger;

public class TeamList extends ListActivity {
    private static final int CREATE_TEAM_DIALOG = 1;
    private static final int CONFIRM_DELETE_DIALOG = 2;

    private View textEntryView = null;
    private Dialog createTeamDialog = null;
    private Dialog confirmDeleteTeamDialog = null;
    private Integer positionOfTeamToDelete = null;
    private ArrayAdapter<String> teamListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_up_timer_teams);
        registerForContextMenu(getListView());
    }

    @Override
    protected void onResume() {
        super.onResume();
        teamListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Team.findAllTeamNames(this));
        setListAdapter(teamListAdapter);
        getListView().setTextFilterEnabled(true);
        getTextEntryView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stand_up_timer_teams_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.add_team:
            Logger.d("Displaying the add team dialog box");
            displayAddTeamDialog();
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
        inflater.inflate(R.menu.stand_up_timer_teams_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
        case R.id.delete_team:
            positionOfTeamToDelete = info.position;
            showDialog(CONFIRM_DELETE_DIALOG);
            return true;
        default:
            return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        String teamName = teamListAdapter.getItem(position);

        Intent intent = new Intent(this, TeamDetails.class);
        intent.putExtra("teamName", teamName);
        startActivity(intent);
    }

    private void deleteTeam(String teamName) {
        Team team = Team.findByName(teamName, this);
        team.delete(this);
    }

    protected void displayAddTeamDialog() {
        showDialog(CREATE_TEAM_DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case CREATE_TEAM_DIALOG:
            if (createTeamDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("add_team");
                builder.setView(getTextEntryView());
                builder.setCancelable(true);
                builder.setPositiveButton("ok", addTeamButtonListener());
                builder.setNegativeButton("revert", cancelListener());
                createTeamDialog = builder.create();
            }
            return createTeamDialog;

        case CONFIRM_DELETE_DIALOG:
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

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        EditText collectedTextView = (EditText) getTextEntryView().findViewById(R.id.collected_text);
        collectedTextView.setText("");
    }

    synchronized protected View getTextEntryView() {
        if (textEntryView == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            textEntryView = factory.inflate(R.layout.stand_up_timer_collect_text, null);
        }
        return textEntryView;
    }

    protected DialogInterface.OnClickListener addTeamButtonListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText collectedTextView = (EditText) getTextEntryView().findViewById(R.id.collected_text);
                String name = collectedTextView.getText().toString();
                Team.create(name, TeamList.this);
                teamListAdapter.add(name);
            }
        };
    }

    protected DialogInterface.OnClickListener deleteTeamConfirmationListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String teamName = teamListAdapter.getItem(positionOfTeamToDelete);
                deleteTeam(teamName);
                teamListAdapter.remove(teamName);
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

    public AlertDialog getCreateTeamDialog() {
        return (AlertDialog) createTeamDialog;
    }

    public AlertDialog getConfirmDeleteTeamDialog() {
        return (AlertDialog) confirmDeleteTeamDialog;
    }
}
