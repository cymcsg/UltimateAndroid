/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.fss.common.uiModule.niftymodaldialogeffects.effects;

import android.view.View;

import com.fss.common.uiModule.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by lee on 2014/7/31.
 */
public class FlipH  extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "rotationY", -90, 0).setDuration(DURATION)

        );
    }
}
