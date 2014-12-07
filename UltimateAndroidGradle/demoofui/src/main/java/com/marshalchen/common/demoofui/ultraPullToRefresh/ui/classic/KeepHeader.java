package com.marshalchen.common.demoofui.ultraPullToRefresh.ui.classic;

import com.marshalchen.common.demoofui.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class KeepHeader extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(PtrClassicFrameLayout ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_keep_header);
        ptrFrame.setKeepHeaderWhenRefresh(true);
    }
}
