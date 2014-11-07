package com.marshalchen.common.uimodule.recyclerviewstickyheaders;

import android.support.v7.widget.RecyclerView;

/**
 * Created by aurel on 16/10/14.
 */
public class StickyHeadersBuilder {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private StickyHeadersAdapter headersAdapter;
    private boolean overlay;


    public StickyHeadersBuilder() {
    }

    public StickyHeadersBuilder setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    public StickyHeadersBuilder setStickyHeadersAdapter(StickyHeadersAdapter adapter) {
        return setStickyHeadersAdapter(adapter, false);
    }

    public StickyHeadersBuilder setStickyHeadersAdapter(StickyHeadersAdapter adapter, boolean overlay) {
        this.headersAdapter = adapter;
        this.overlay = overlay;
        return this;
    }

    public StickyHeadersBuilder setAdapter(RecyclerView.Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("Adapter must have stable ids");
        }
        this.adapter = adapter;
        return this;
    }

    public StickyHeadersItemDecoration build() {


        StickyHeadersItemDecoration decoration =  new StickyHeadersItemDecoration(headersAdapter, recyclerView, overlay);

        decoration.registerAdapterDataObserver(adapter);

        return decoration;
    }
}
