package com.marshalchen.common.uimodule.androidanimations.specials.outs;

import android.view.View;
import com.marshalchen.common.uimodule.androidanimations.BaseViewAnimator;
import com.marshalchen.common.uimodule.easing.Glider;
import com.marshalchen.common.uimodule.easing.Skill;
import com.marshalchen.common.uimodule.nineoldandroids.animation.ObjectAnimator;


public class TakingOffAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 1, 0))
        );
    }
}
