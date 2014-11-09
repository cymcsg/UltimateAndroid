package com.marshalchen.common.demoofui.superlistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.superlistview.OnMoreListener;
import com.marshalchen.common.uimodule.superlistview.SuperGridview;


import java.util.ArrayList;


public class GridSample extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    private SuperGridview mList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_list_view_activity_grid_sample);


        // Empty list view demo, just pull to add more items
        ArrayList<String> lst = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lst);


        // This is what you're looking for
        mList = (SuperGridview) findViewById(R.id.list);
        mList.setAdapter(mAdapter);

        // Setting the refresh listener will enable the refresh progressbar
        mList.setRefreshListener(this);

        // Wow so beautiful
        mList.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        // I want to get loadMore triggered if I see the last item (1)
        mList.setupMoreListener(this, 1);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();

        // enjoy the beaty of the progressbar
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                // demo purpose, adding to the top so you can see it
                mAdapter.insert("New stuff", 0);

            }
        }, 2000);


    }

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        Toast.makeText(this, "More", Toast.LENGTH_LONG).show();

        //demo purpose, adding to the bottom
        mAdapter.add("More asked, more served");

    }
}
