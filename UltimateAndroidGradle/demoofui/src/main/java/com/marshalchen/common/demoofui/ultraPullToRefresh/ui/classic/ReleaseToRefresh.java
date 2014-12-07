package com.marshalchen.common.demoofui.ultraPullToRefresh.ui.classic;

import com.marshalchen.common.demoofui.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class ReleaseToRefresh extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(PtrClassicFrameLayout ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_release_to_refresh);
        ptrFrame.setPullToRefresh(false);
    }
}
