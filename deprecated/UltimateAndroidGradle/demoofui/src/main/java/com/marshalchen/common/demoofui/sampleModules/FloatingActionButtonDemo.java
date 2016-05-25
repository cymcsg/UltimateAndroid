package com.marshalchen.common.demoofui.sampleModules;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.FloatingActionButtonWithListView.FloatingActionButton;


public class FloatingActionButtonDemo extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_action_button_demo);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.button_floating_action);
        floatingActionButton.attachToListView(getListView());

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.countries));
        getListView().setAdapter(listAdapter);
    }
}