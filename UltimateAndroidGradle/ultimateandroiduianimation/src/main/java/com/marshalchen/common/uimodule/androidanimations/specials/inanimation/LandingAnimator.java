package com.marshalchen.common.uimodule.androidanimations.specials.inanimation;

import android.view.View;

import com.marshalchen.common.uimodule.androidanimations.BaseViewAnimator;

import com.marshalchen.common.uimodule.easing.Glider;
import com.marshalchen.common.uimodule.easing.Skill;
import com.nineoldandroids.animation.ObjectAnimator;

public class LandingAnimator extends BaseViewAnimator{
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 0, 1f))
        );
    }
}
