package com.marshalchen.common.uimodule.androidanimations.specials.in;

import android.view.View;

import com.marshalchen.common.uimodule.androidanimations.BaseViewAnimator;

import com.marshalchen.common.uimodule.easing.Glider;
import com.marshalchen.common.uimodule.easing.Skill;
import com.marshalchen.common.uimodule.nineoldandroids.animation.ObjectAnimator;

public class DropOutAnimator extends BaseViewAnimator{
    @Override
    protected void prepare(View target) {
        int distance = target.getTop() + target.getHeight();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                Glider.glide(Skill.BounceEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "translationY", -distance, 0))
        );
    }
}
