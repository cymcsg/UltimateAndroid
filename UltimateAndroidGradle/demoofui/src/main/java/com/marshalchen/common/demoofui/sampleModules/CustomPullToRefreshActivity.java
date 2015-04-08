package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.widget.PullRefreshLayout;


public class CustomPullToRefreshActivity extends Activity {

    PullRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_pull_to_refresh_activity_demo);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] array = new String[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = "string " + i;
        }
        recyclerView.setAdapter(new ArrayAdapter(this, array));

        layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                    }
                }, 4000);
            }
        });
    }

    static class ArrayAdapter extends RecyclerView.Adapter<ViewHolder> {

        private String[] mArray;
        private Context mContext;

        public ArrayAdapter(Context context, String[] array) {
            mContext = context;
            mArray = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(View.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.mTextView.setText(mArray[i]);
        }

        @Override
        public int getItemCount() {
            return mArray.length;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_pull_to_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_circles:
                layout.setRefreshStyle(PullRefreshLayout.STYLE_CIRCLES);
                return true;
            case R.id.action_water_drop:
                layout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
                return true;
            case R.id.action_ring:
                layout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
