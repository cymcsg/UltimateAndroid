package com.marshalchen.common.usefulModule;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.marshalchen.common.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
/**
 * UltimateListView which can pull to refresh using the SwipeRefreshLayout and load more
 */
public class UltimateListview extends LinearLayout {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected BasicUltimateListView mBasicUltimateListView;

    public UltimateListview(Context context) {
        this(context, null);
    }

    public UltimateListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }


    public UltimateListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ultimate_listview_layout, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ultimate_listview_swipe_layout);
        mBasicUltimateListView = (BasicUltimateListView) view.findViewById(R.id.basicUltimateListView);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public BasicUltimateListView getListView() {
        return mBasicUltimateListView;
    }

    public void setAdapter(ListAdapter adapter) {

//        mSwipeRefreshLayout.setRefreshing(false);
//        adapter.registerDataSetObserver(new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                mSwipeRefreshLayout.setRefreshing(false);
//
//            }
//        });
        mBasicUltimateListView.setAdapter(adapter);
//        if ((adapter == null || adapter.getCount() == 0) && mEmptyId != 0) {
//            mEmpty.setVisibility(View.VISIBLE);
//        }
    }

    /**
     * Set the listener when refresh is triggered and enable the SwipeRefreshLayout
     *
     * @param listener
     */
    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void setRefreshingColor(int col1, int col2, int col3, int col4) {
        mSwipeRefreshLayout.setColorScheme(col1, col2, col3, col4);
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mBasicUltimateListView.setOnItemClickListener(listener);
    }

    public ListAdapter getAdapter() {
        return mBasicUltimateListView.getAdapter();
    }
}
