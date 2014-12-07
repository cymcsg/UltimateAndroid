package com.marshalchen.common.demoofui.ultraPullToRefresh.ui.classic;

import com.marshalchen.common.demoofui.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class PullToRefresh extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(PtrClassicFrameLayout ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_pull_to_refresh);
        ptrFrame.setPullToRefresh(true);
    }
}
