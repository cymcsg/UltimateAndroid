package com.marshalchen.common.usefulModule;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;
import com.marshalchen.common.commonUtils.logUtils.Logs;

/**
 * A component in UltimateListView which provide load more
 */
public class BasicUltimateListView extends ListView {

    int currentFirstVisibleItem;
    int currentVisibleItemCount;
    int currentScrollState;
    int totalItemCounts;
    boolean isLoading = false;

    public BasicUltimateListView(Context context) {
        super(context);
        init();
    }

    public BasicUltimateListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasicUltimateListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void isScrollCompleted() {
        if (this.currentVisibleItemCount > 0 && this.currentScrollState == OnScrollListener.SCROLL_STATE_IDLE && (currentFirstVisibleItem + currentVisibleItemCount) >= totalItemCounts - 1) {

            if (!isLoading) {
                isLoading = true;
                onLoadMore();
            }
        }
    }


    private void init() {
        setOnScrollListener(loadMoreOnScrollListener);
    }

    OnScrollListener loadMoreOnScrollListener = new OnScrollListener() {


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            currentScrollState = scrollState;
            isScrollCompleted();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            currentFirstVisibleItem = firstVisibleItem;
            currentVisibleItemCount = visibleItemCount;
            totalItemCounts = totalItemCount;
        }
    };
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {

        public void loadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void onLoadMore() {
        onLoadMoreListener.loadMore();
        isLoading = false;
    }

//    /**
//     * Remove the adapter from the listview
//     */
//    public void clear() {
//        setAdapter(null);
//    }
//
//    ;

}
