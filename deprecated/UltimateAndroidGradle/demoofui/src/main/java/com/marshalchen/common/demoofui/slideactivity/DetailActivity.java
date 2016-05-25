package com.marshalchen.common.demoofui.slideactivity;

import android.os.Bundle;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.slideactivity.SlidingActivity;

/**
 * Created by chenjishi on 14-3-17.
 */
public class DetailActivity extends SlidingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_activity_activity_detail);
    }
}
