package com.marshalchen.common.demoofui.snackbar;

import com.marshalchen.common.demoofui.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SnackbarListViewSampleActivity extends ActionBarActivity {

    private static final String TAG = SnackbarListViewSampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack_bar_activity_list_sample);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView listView = (ListView) findViewById(android.R.id.list);

        List<String> data = new ArrayList<String>();

        for(int i = 0; i < 25; i++) {
            data.add(String.format("Item %d", (i + 1)));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarListViewSampleActivity.this)
                                .text(String.format("Item %d pressed", (position + 1)))
                                .actionLabel("Close")
                                .actionColor(Color.parseColor("#FF8A80"))
                                .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                                .attachToAbsListView(listView));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}