package com.marshalchen.common.demoofui.ultraPullToRefresh.ui.classic;

import com.marshalchen.common.demoofui.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class AutoRefresh extends WithGridView {

    @Override
    protected void setupViews(final PtrClassicFrameLayout ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_auto_fresh);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoRefresh();
            }
        }, 150);
    }
}