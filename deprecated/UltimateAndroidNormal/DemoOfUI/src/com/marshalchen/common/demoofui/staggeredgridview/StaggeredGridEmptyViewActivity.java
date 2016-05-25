package com.marshalchen.common.demoofui.staggeredgridview;

import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.staggeredgridview.StaggeredGridView;


import java.util.ArrayList;

public class StaggeredGridEmptyViewActivity extends Activity implements AbsListView.OnItemClickListener {

    public static final String SAVED_DATA_KEY = "SAVED_DATA";
    private static final int FETCH_DATA_TASK_DURATION = 2000;
    private static final String FETCH_DATA_FILTER = "StaggeredGridEmptyViewActivity_fetchDataReceiver";

    private StaggeredGridView mGridView;
    private SampleAdapter mAdapter;

    private ArrayList<String> mData;
    private BroadcastReceiver fetchDataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staggered_grid_view_activity_sgv_empy_view);

        setTitle("SGV");
        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

        LayoutInflater layoutInflater = getLayoutInflater();

        View header = layoutInflater.inflate(R.layout.staggered_grid_view_list_item_header_footer, null);
        View footer = layoutInflater.inflate(R.layout.staggered_grid_view_list_item_header_footer, null);
        TextView txtHeaderTitle = (TextView) header.findViewById(R.id.txt_title);
        TextView txtFooterTitle =  (TextView) footer.findViewById(R.id.txt_title);
        txtHeaderTitle.setText("THE HEADER!");
        txtFooterTitle.setText("THE FOOTER!");

        mGridView.addHeaderView(header);
        mGridView.addFooterView(footer);
        mGridView.setEmptyView(findViewById(android.R.id.empty));
        mAdapter = new SampleAdapter(this, R.id.txt_line1);

        // do we have saved data?
        if (savedInstanceState != null) {
            mData = savedInstanceState.getStringArrayList(SAVED_DATA_KEY);
            fillAdapter();
        }

        if (mData == null) {
            mData = SampleData.generateSampleData();
        }


        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(this);

        fetchDataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context receiverContext, Intent receiverIntent) {
                fillAdapter();
            }
        };
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(fetchDataReceiver, new IntentFilter(FETCH_DATA_FILTER));
        fetchData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fetchDataReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(fetchDataReceiver);
    }

    private void fillAdapter() {
        for (String data : mData) {
            mAdapter.add(data);
        }
    }

    private void fetchData() {
        Intent fetchData = new Intent(this, FetchDataService.class);
        this.startService(fetchData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.staggered_grid_view_activity_sgv_empty_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAdapter.clear();
        fetchData();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }

    public static class FetchDataService extends IntentService {
        public FetchDataService() {
            super("FetchDataService");
        }

        public void onHandleIntent(Intent intent) {
            SystemClock.sleep(FETCH_DATA_TASK_DURATION);
            Intent resultIntent = new Intent(FETCH_DATA_FILTER);
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
        }
    }
}
