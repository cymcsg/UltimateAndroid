package com.marshalchen.common.uimodule.superlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import com.marshalchen.common.uimodule.R;


/**
 * Created by kentin on 24/04/14.
 */
public abstract class BaseSuperAbsListview extends FrameLayout implements AbsListView.OnScrollListener {

    protected int ITEM_LEFT_TO_LOAD_MORE = 10;

    protected ViewStub mProgress;
    protected ViewStub mMoreProgress;
    protected AbsListView mList;
    protected ViewStub mEmpty;

    protected float mDividerHeight;
    protected int mDivider;
    protected boolean mClipToPadding;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mScrollbarStyle;
    protected int mEmptyId;
    protected int mMoreProgressId;

    protected AbsListView.OnScrollListener mOnScrollListener;

    protected OnMoreListener mOnMoreListener;
    protected boolean isLoadingMore;
    protected int mSelector;
    protected SwipeRefreshLayout mPtrLayout;

    protected int mSuperListViewMainLayout;
    private int mProgressId;

    public SwipeRefreshLayout getSwipeToRefresh() {
        return mPtrLayout;
    }

    public AbsListView getList() {
        return mList;
    }

    public BaseSuperAbsListview(Context context) {
        super(context);
        initView();
    }

    public BaseSuperAbsListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public BaseSuperAbsListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.superlistview);
        try {
            mClipToPadding = a.getBoolean(R.styleable.superlistview_superlv__listClipToPadding, false);
            mDivider = a.getColor(R.styleable.superlistview_superlv__listDivider, 0);
            mDividerHeight = a.getDimension(R.styleable.superlistview_superlv__listDividerHeight, 0.0f);
            mPadding = (int) a.getDimension(R.styleable.superlistview_superlv__listPadding, -1.0f);
            mPaddingTop = (int) a.getDimension(R.styleable.superlistview_superlv__listPaddingTop, 0.0f);
            mPaddingBottom = (int) a.getDimension(R.styleable.superlistview_superlv__listPaddingBottom, 0.0f);
            mPaddingLeft = (int) a.getDimension(R.styleable.superlistview_superlv__listPaddingLeft, 0.0f);
            mPaddingRight = (int) a.getDimension(R.styleable.superlistview_superlv__listPaddingRight, 0.0f);
            mScrollbarStyle = a.getInt(R.styleable.superlistview_superlv__scrollbarStyle, -1);
            mEmptyId = a.getResourceId(R.styleable.superlistview_superlv__empty, 0);
            mMoreProgressId = a.getResourceId(R.styleable.superlistview_superlv__moreProgress, R.layout.super_list_view_view_more_progress);
            mProgressId = a.getResourceId(R.styleable.superlistview_superlv__progress, R.layout.super_list_view_view_progress_gridview);
            mSelector = a.getResourceId(R.styleable.superlistview_superlv__listSelector, 0);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        View v = LayoutInflater.from(getContext()).inflate(mSuperListViewMainLayout, this);
        mPtrLayout = (SwipeRefreshLayout) v.findViewById(R.id.ptr_layout);
        mPtrLayout.setEnabled(false);

        mProgress = (ViewStub) v.findViewById(android.R.id.progress);

        mProgress.setLayoutResource(mProgressId);
        mProgress.inflate();

        mMoreProgress = (ViewStub) v.findViewById(R.id.more_progress);
        mMoreProgress.setLayoutResource(mMoreProgressId);
        if (mMoreProgressId != 0)
            mMoreProgress.inflate();
        mMoreProgress.setVisibility(View.GONE);

        mEmpty = (ViewStub) v.findViewById(R.id.empty);
        mEmpty.setLayoutResource(mEmptyId);
        if (mEmptyId != 0)
            mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);

        initAbsListView(v);
    }

    /**
     * Implement this method to customize the AbsListView
     */
    protected abstract void initAbsListView(View view);


    /**
     * Set the adapter to the listview
     * Automativally hide the progressbar
     * Set the refresh to false
     * If adapter is empty, then the emptyview is shown
     *
     * @param adapter
     */
    public void setAdapter(ListAdapter adapter) {
        mProgress.setVisibility(View.GONE);
        if (mEmpty != null && mEmptyId != 0)
            mList.setEmptyView(mEmpty);
        mList.setVisibility(View.VISIBLE);
        mPtrLayout.setRefreshing(false);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mProgress.setVisibility(View.GONE);
                isLoadingMore = false;
                mPtrLayout.setRefreshing(false);
                if (mList.getAdapter().getCount() == 0 && mEmptyId != 0) {
                    mEmpty.setVisibility(View.VISIBLE);
                } else if (mEmptyId != 0) {
                    mEmpty.setVisibility(View.GONE);
                }
            }
        });
        if ((adapter == null || adapter.getCount() == 0) && mEmptyId != 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Remove the adapter from the listview
     */
    public abstract void clear();

    /**
     * Show the progressbar
     */
    public void showProgress() {
        hideList();
        if (mEmptyId != 0) mEmpty.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progressbar and show the listview
     */
    public void showList() {
        hideProgress();
        mList.setVisibility(View.VISIBLE);
    }

    public void showMoreProgress() {
        mMoreProgress.setVisibility(View.VISIBLE);

    }

    public void hideMoreProgress() {
        mMoreProgress.setVisibility(View.GONE);

    }

    /**
     * Set the listener when refresh is triggered and enable the SwipeRefreshLayout
     *
     * @param listener
     */
    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mPtrLayout.setEnabled(true);
        mPtrLayout.setOnRefreshListener(listener);
    }

    /**
     * Set the colors for the SwipeRefreshLayout states
     *
     * @param col1
     * @param col2
     * @param col3
     * @param col4
     */
    public void setRefreshingColor(int col1, int col2, int col3, int col4) {
        mPtrLayout.setColorScheme(col1, col2, col3, col4);
    }

    /**
     * Hide the progressbar
     */
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    /**
     * Hide the listview
     */
    public void hideList() {
        mList.setVisibility(View.GONE);
    }

    /**
     * Set the scroll listener for the listview
     *
     * @param listener
     */
    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    /**
     * Set the onItemClickListener for the listview
     *
     * @param listener
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mList.setOnItemClickListener(listener);
    }

    /**
     * @return the listview adapter
     */
    public ListAdapter getAdapter() {
        return mList.getAdapter();
    }

    private int getFirstVisiblePosition() {
        return mList.getFirstVisiblePosition();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) mOnScrollListener.onScrollStateChanged(view, scrollState);
    }

    /**
     * Sets the More listener
     *
     * @param onMoreListener
     * @param max            Number of items before loading more
     */
    public void setupMoreListener(OnMoreListener onMoreListener, int max) {
        mOnMoreListener = onMoreListener;
        ITEM_LEFT_TO_LOAD_MORE = max;
    }

    public void setOnMoreListener(OnMoreListener onMoreListener) {
        mOnMoreListener = onMoreListener;
    }

    public void setNumberBeforeMoreIsCalled(int max) {
        ITEM_LEFT_TO_LOAD_MORE = max;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    /**
     * Enable/Disable the More event
     *
     * @param isLoadingMore
     */
    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if (((totalItemCount - firstVisibleItem - visibleItemCount) == ITEM_LEFT_TO_LOAD_MORE || (totalItemCount - firstVisibleItem - visibleItemCount) == 0 && totalItemCount > visibleItemCount) && !isLoadingMore) {
            isLoadingMore = true;
            if (mOnMoreListener != null) {
                mMoreProgress.setVisibility(View.VISIBLE);
                mOnMoreListener.onMoreAsked(mList.getAdapter().getCount(), ITEM_LEFT_TO_LOAD_MORE, firstVisibleItem);

            }
        }
        if (mOnScrollListener != null)
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    /**
     * Remove the moreListener
     */
    public void removeMoreListener() {
        mOnMoreListener = null;
    }


    public void setOnTouchListener(OnTouchListener listener) {
        mList.setOnTouchListener(listener);
    }
}
