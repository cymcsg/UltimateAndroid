package com.fss.common.demo.superlistview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.fss.common.demo.R;
import com.fss.common.uiModule.superlistview.OnMoreListener;
import com.fss.common.uiModule.superlistview.SuperListview;
import com.fss.common.uiModule.superlistview.SwipeDismissListViewTouchListener;


import java.util.ArrayList;


public class ListSample extends Activity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    private SuperListview mList;
    private ArrayAdapter<String> mAdapter;

    @SuppressWarnings("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_list_view_activity_list_sample);

        // Empty list view demo, just pull to add more items
        ArrayList<String> lst = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lst);


        // This is what you're looking for
        mList = (SuperListview) findViewById(R.id.list);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.add("More stuff1");
                        mAdapter.add("More stuff2");
                        mAdapter.add("More stuff3");
                        mAdapter.add("More stuff4");
                        mAdapter.add("More stuff5");
                        mAdapter.add("More stuff6");
                        mAdapter.add("More stuff7");


                        mList.setAdapter(mAdapter);

                    }
                });
            }
        });
        thread.start();

        // Setting the refresh listener will enable the refresh progressbar
        mList.setRefreshListener(this);

        // Wow so beautiful
        mList.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        // I want to get loadMore triggered if I see the last item (1)
        mList.setupMoreListener(this, 1);

        mList.setupSwipeToDismiss(new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            }
        }, true);
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
        }, 200);


    }

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

        Toast.makeText(this, "More", Toast.LENGTH_LONG).show();

        //demo purpose, adding to the bottom
        mAdapter.add("More asked, more served");
    }
}
