/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.fss.common.demo.fancyCoverFlow.example;

import android.app.Activity;
import android.os.Bundle;
import com.fss.common.demo.R;
import com.fss.common.demo.fancyCoverFlow.shared.FancyCoverFlowSampleAdapter;
import com.fss.common.uiModule.fancycoverflow.FancyCoverFlow;


public class SimpleExample extends Activity {

    // =============================================================================
    // Child views
    // =============================================================================

    private FancyCoverFlow fancyCoverFlow;

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fancy_cover_flow_activity_main);

        this.fancyCoverFlow = (FancyCoverFlow) this.findViewById(R.id.fancyCoverFlow);

        this.fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter());
        this.fancyCoverFlow.setUnselectedAlpha(1.0f);
        this.fancyCoverFlow.setUnselectedSaturation(0.0f);
        this.fancyCoverFlow.setUnselectedScale(0.5f);
        this.fancyCoverFlow.setSpacing(50);
        this.fancyCoverFlow.setMaxRotation(0);
        this.fancyCoverFlow.setScaleDownGravity(0.2f);
        this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
    }

    // =============================================================================
    // Private classes
    // =============================================================================



}
