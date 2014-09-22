/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.fancyCoverFlow.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.fancycoverflow.FancyCoverFlow;
import com.marshalchen.common.uimodule.fancycoverflow.FancyCoverFlowAdapter;


public class ViewGroupReflectionExample extends Activity {

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fancy_cover_flow_layout_inflate_example);

        FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        fancyCoverFlow.setReflectionEnabled(true);
        fancyCoverFlow.setReflectionRatio(0.3f);
        fancyCoverFlow.setReflectionGap(0);

        fancyCoverFlow.setAdapter(new ViewGroupExampleAdapter());
    }

    // =============================================================================
    // Private classes
    // =============================================================================

    private static class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

        // =============================================================================
        // Private members
        // =============================================================================

        private int[] images = {R.drawable.test, R.drawable.test_back2, R.drawable.test_back, R.drawable.test_back1, R.drawable.test, R.drawable.test,};

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
            CustomViewGroup customViewGroup = null;

            if (reuseableView != null) {
                customViewGroup = (CustomViewGroup) reuseableView;
            } else {
                customViewGroup = new CustomViewGroup(viewGroup.getContext());
                customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 600));
            }

            customViewGroup.getImageView().setImageResource(this.getItem(i));

            return customViewGroup;
        }
    }

    private static class CustomViewGroup extends LinearLayout {

        // =============================================================================
        // Child views
        // =============================================================================

        private ImageView imageView;

        private Button button;

        // =============================================================================
        // Constructor
        // =============================================================================

        private CustomViewGroup(Context context) {
            super(context);

            this.setOrientation(VERTICAL);
            this.setWeightSum(5);

            this.imageView = new ImageView(context);
            this.button = new Button(context);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            this.imageView.setLayoutParams(layoutParams);
            this.button.setLayoutParams(layoutParams);

            this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            this.imageView.setAdjustViewBounds(true);

            this.button.setText("Goto GitHub");
            this.button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://davidschreiber.github.com/FancyCoverFlow"));
                    view.getContext().startActivity(i);
                }
            });

            this.addView(this.imageView);
            this.addView(this.button);
        }

        // =============================================================================
        // Getters
        // =============================================================================

        private ImageView getImageView() {
            return imageView;
        }
    }
}
