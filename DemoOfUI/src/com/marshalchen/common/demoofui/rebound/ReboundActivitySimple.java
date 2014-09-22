/*
 *  Copyright (c) 2013, Facebook, Inc.
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree. An additional grant
 *  of patent rights can be found in the PATENTS file in the same directory.
 *
 */

package com.marshalchen.common.demoofui.rebound;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.uimodule.rebound.*;
import com.marshalchen.common.demoofui.R;

public class ReboundActivitySimple extends Activity {
    SpringSystem springSystem = SpringSystem.create();
    Spring mScaleSpring;
    private final ExampleSpringListener mSpringListener = new ExampleSpringListener();
    @InjectView(R.id.reboundImageView)
    ImageView reboundImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rebound_activity_simple);
        ButterKnife.inject(this);
        mScaleSpring = springSystem.createSpring();
        SpringConfig config = SpringConfig.defaultConfig;
        config.tension = 700.0f;
        config.friction = 125.0f;
        mScaleSpring.setSpringConfig(config);
        reboundImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mScaleSpring.setEndValue(1);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mScaleSpring.setEndValue(0);
                        break;
                }
                return true;
            }
        });
        Logs.d("tension--" + mScaleSpring.getSpringConfig().tension + "   " + mScaleSpring.getSpringConfig().friction);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScaleSpring.addListener(mSpringListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScaleSpring.removeListener(mSpringListener);
    }

    private class ExampleSpringListener extends SimpleSpringListener {
        @Override
        public void onSpringUpdate(Spring spring) {
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0.5);
            reboundImageView.setScaleX(mappedValue);
            reboundImageView.setScaleY(mappedValue);
        }
    }
}
