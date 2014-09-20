package com.marshalchen.common.uiModule.niftymodaldialogeffects.effects;

import android.view.View;
import android.widget.RelativeLayout;

import com.marshalchen.common.uiModule.nineoldandroids.animation.ObjectAnimator;
import com.marshalchen.common.uiModule.nineoldandroids.view.ViewHelper;

/**
 * Created by lee on 2014/7/31.
 */
public class RotateBottom extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "rotationX",90, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "translationY", 300, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2)

        );
    }
}
