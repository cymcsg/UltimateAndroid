/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.fss.common.demo.fancyCoverFlow.example;

import android.os.Bundle;
import com.fss.common.demo.R;
import com.fss.common.demo.fancyCoverFlow.shared.FancyCoverFlowSampleAdapter;
import com.fss.common.uiModule.fancycoverflow.FancyCoverFlow;


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
