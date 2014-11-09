package com.marshalchen.common.uimodule.superlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import com.marshalchen.common.uimodule.R;


/**
 * Created by kentin on 24/04/14.
 */
public class SuperGridview extends BaseSuperAbsListview {

    //-------------------------------------------------------
    // Custom Grid attributes
    //-------------------------------------------------------
    private int mColumns;
    private int mHorizontalSpacing;
    private int mVerticalSpacing;


    public SuperGridview(Context context) {
        super(context);
    }

    public SuperGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public GridView getList(){
        return (GridView) mList;
    }

    @Override
    protected void initAttrs(AttributeSet attrs) {
        super.initAttrs(attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.superlistview);
        try {
            mSuperListViewMainLayout = a.getResourceId(R.styleable.superlistview_superlv_mainLayoutID, R.layout.super_list_view_view_progress_gridview);
        } finally {
            a.recycle();
        }

        TypedArray ag = getContext().obtainStyledAttributes(attrs, R.styleable.supergridview);
        try {
            mColumns = ag.getInt(R.styleable.supergridview_supergv__columns, 1);
            mVerticalSpacing = (int) ag.getDimension(R.styleable.supergridview_supergv__verticalSpacing, 1);
            mHorizontalSpacing = (int) ag.getDimension(R.styleable.supergridview_supergv__horizontalSpacing, 1);
        } finally {
            ag.recycle();
        }
    }

    @Override
    protected void initAbsListView(View v) {

        View listView = v.findViewById(android.R.id.list);
        if (listView instanceof GridView)
            mList = (GridView) listView;
        else
            throw new IllegalArgumentException(listView.getClass().getName());

        if (mList!=null) {

            getList().setNumColumns(mColumns);
            getList().setVerticalSpacing(mVerticalSpacing);
            getList().setHorizontalSpacing(mHorizontalSpacing);
            getList().setHorizontalSpacing((int) mDividerHeight);
            getList().setVerticalSpacing((int) mDividerHeight);

            mList.setClipToPadding(mClipToPadding);

            mList.setOnScrollListener(this);
            if (mSelector != 0)
                mList.setSelector(mSelector);

            if (mPadding != -1.0f) {
                mList.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mList.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }

            mList.setScrollBarStyle(mScrollbarStyle);
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        getList().setAdapter(adapter);
        super.setAdapter(adapter);
    }

    @Override
    public void clear() {
        getList().setAdapter(null);
    }

}
