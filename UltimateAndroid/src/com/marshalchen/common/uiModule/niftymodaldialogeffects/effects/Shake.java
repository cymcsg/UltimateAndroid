/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.uiModule.niftymodaldialogeffects.effects;

import android.view.View;

import com.marshalchen.common.uiModule.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by lee on 2014/7/31.
 */
public class Shake  extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, .10f, -25, .26f, 25,.42f, -25, .58f, 25,.74f,-25,.90f,1,0).setDuration(DURATION)

        );
    }
}
