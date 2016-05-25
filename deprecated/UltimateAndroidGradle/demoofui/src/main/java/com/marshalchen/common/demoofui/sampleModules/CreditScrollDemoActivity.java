/*
 * Copyright 2014 Frakbot (Sebastiano Poggi and Francesco Pontillo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;
import android.widget.Toast;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.simplemodule.CreditsRollView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class CreditScrollDemoActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private static final float SCROLL_ANIM_DURATION = 30000;    // [ms] = 30 s

    private CreditsRollView mCreditsRollView;
    private boolean mScrolling;
    private SeekBar mSeekBar;
    private ValueAnimator mScrollAnimator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_roll_activity_main);

        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);

        mCreditsRollView = (CreditsRollView) findViewById(R.id.creditsroll);
        mCreditsRollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mScrolling) {
                    animateScroll();
                }
                else {
                    stopScrollAnimation();
                }
            }
        });

        Toast.makeText(this, "Tap the empty space to animate the credits roll, or use the scrollbar",
                       Toast.LENGTH_SHORT)
             .show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mCreditsRollView.setScrollPosition(progress / 100000f); // We have increments of 1/100000 %
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mScrolling) {
            stopScrollAnimation();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Don't care
    }

    private void animateScroll() {
        mScrolling = true;
        mScrollAnimator = ObjectAnimator.ofInt(mSeekBar, "progress", mSeekBar.getProgress(), mSeekBar.getMax());
        mScrollAnimator.setDuration(
            (long) (SCROLL_ANIM_DURATION * (1 - (float) mSeekBar.getProgress() / mSeekBar.getMax())));
        mScrollAnimator.setInterpolator(new LinearInterpolator());
        mScrollAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Don't care
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mScrolling = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Don't care
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Don't care
            }
        });
        mScrollAnimator.start();
    }

    private void stopScrollAnimation() {
        if (mScrollAnimator != null) {
            mScrollAnimator.cancel();
            mScrollAnimator = null;
        }
    }
}
