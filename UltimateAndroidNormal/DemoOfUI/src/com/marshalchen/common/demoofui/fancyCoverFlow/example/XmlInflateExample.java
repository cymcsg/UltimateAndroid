/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.fancyCoverFlow.example;

import android.os.Bundle;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.fancyCoverFlow.shared.FancyCoverFlowSampleAdapter;
import com.marshalchen.common.uimodule.fancycoverflow.FancyCoverFlow;


public class XmlInflateExample extends SimpleExample {

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fancy_cover_flow_layout_inflate_example);

        FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter());
    }

}
