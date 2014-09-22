/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.fancyCoverFlow.shared;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.fancycoverflow.FancyCoverFlow;
import com.marshalchen.common.uimodule.fancycoverflow.FancyCoverFlowAdapter;


public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {

    // =============================================================================
    // Private members
    // =============================================================================

    private int[] images = {R.drawable.test, R.drawable.test_back, R.drawable.test_back1, R.drawable.test_back2, R.drawable.test, R.drawable.test};

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Integer getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
        ImageView imageView = null;

        if (reuseableView != null) {
            imageView = (ImageView) reuseableView;
        } else {
            imageView = new ImageView(viewGroup.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 400));

        }

        imageView.setImageResource(this.getItem(i));
        return imageView;
    }
}
