package com.marshalchen.common.uimodule.recyclerviewstickyheaders;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewHelper;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by aurel on 22/09/14.
 */
public class StickyHeadersItemDecoration extends RecyclerView.ItemDecoration {

    private final static int NO_HEIGHT = -1;

    private final StickyHeadersAdapter adapter;
    private final RecyclerView parent;
    private final RecyclerView.ViewHolder headerViewHolder;
    private final HashMap<Long, Boolean> headers;
    private final AdapterDataObserver adapterDataObserver;
    private boolean overlay;
    private int headerHeight;

    public StickyHeadersItemDecoration(StickyHeadersAdapter adapter, RecyclerView parent) {
        this(adapter, parent, false);
    }

    public StickyHeadersItemDecoration(StickyHeadersAdapter adapter, RecyclerView parent, boolean overlay) {
        this.adapter = adapter;
        this.parent = parent;
        this.headerViewHolder = adapter.onCreateViewHolder(parent);
        this.overlay = overlay;
        this.headers = new HashMap<Long, Boolean>();
        this.adapterDataObserver = new AdapterDataObserver();
        this.headerHeight = NO_HEIGHT;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {


        final int childCount = parent.getChildCount();
        final RecyclerView.LayoutManager lm = parent.getLayoutManager();
        View header = headerViewHolder.itemView;
        Float lastY = null;

        for (int i = childCount - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int position = parent.getChildPosition(child);
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(child);

            if (!lp.isItemRemoved()) {

                float translationY = ViewCompat.getTranslationY(child);

                if (i == 0 || isHeader(holder)) {

                    float y = getHeaderY(child, lm) + translationY;

                    if (lastY != null && lastY < y + headerHeight) {
                        y = lastY - headerHeight;
                    }

                    adapter.onBindViewHolder(headerViewHolder, position);

                    c.save();
                    c.translate(0, y);
                    header.draw(c);
                    c.restore();

                    lastY = y;
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        ensureHeaderLaidOut();

        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams)view.getLayoutParams();
        RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);

        if (overlay || !isHeader(holder)) {
            outRect.set(0, 0, 0, 0);
        }
        else {
            //TODO: Handle layout direction
            outRect.set(0, headerHeight, 0, 0);
        }

        if (lp.isItemRemoved()) {
            headers.remove(holder.getItemId());
        }
    }

    public void registerAdapterDataObserver(RecyclerView.Adapter adapter) {
        adapter.registerAdapterDataObserver(adapterDataObserver);
    }

    private float getHeaderY(View item, RecyclerView.LayoutManager lm) {
        return  lm.getDecoratedTop(item) < 0 ? 0 : lm.getDecoratedTop(item);
    }

    private Boolean isHeader(RecyclerView.ViewHolder holder) {
        if (!headers.containsKey(holder.getItemId())  || headers.get(holder.getItemId()) == null) {
            int itemPosition = RecyclerViewHelper.convertPreLayoutPositionToPostLayout(parent, holder.getPosition());

            if (itemPosition == 0) {
                headers.put(holder.getItemId(), true);
            }
            else {
                headers.put(holder.getItemId(), adapter.getHeaderId(itemPosition) != adapter.getHeaderId(itemPosition -1));
            }
        }

        return headers.get(holder.getItemId());
    }


    private void ensureHeaderLaidOut() {
        if (headerHeight == NO_HEIGHT) {
            int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            headerViewHolder.itemView.measure(widthSpec, heightSpec);
            headerViewHolder.itemView.layout(0, 0, headerViewHolder.itemView.getMeasuredWidth(), headerViewHolder.itemView.getMeasuredHeight());
            headerHeight = headerViewHolder.itemView.getMeasuredHeight();
        }
    }


    private class AdapterDataObserver extends RecyclerView.AdapterDataObserver {

        public AdapterDataObserver() {
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            RecyclerView.ViewHolder holder = parent.findViewHolderForPosition(positionStart + 1);
            if (holder != null) {
                headers.put(holder.getItemId(), null);
            }
            else {
                cleanOffScreenItemsIds();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            RecyclerView.ViewHolder holder = parent.findViewHolderForPosition(positionStart);
            if (holder != null) {
                headers.put(holder.getItemId(), null);
            }
            else {
                cleanOffScreenItemsIds();
            }
        }

        private void cleanOffScreenItemsIds() {
            Iterator<Long> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                long itemId = iterator.next();
                if (parent.findViewHolderForItemId(itemId) == null) {
                    iterator.remove();
                }
            }
        }
    }

}
