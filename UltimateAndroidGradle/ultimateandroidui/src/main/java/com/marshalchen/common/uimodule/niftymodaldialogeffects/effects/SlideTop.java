package com.marshalchen.common.uimodule.niftymodaldialogeffects.effects;

import android.view.View;
import com.marshalchen.common.uimodule.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by lee on 2014/7/31.
 */
public class SlideTop extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationY", -300, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2)

        );
    }
}
