/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.fss.common.uiModule.niftymodaldialogeffects.effects;

import android.view.View;

import com.fss.common.uiModule.nineoldandroids.view.ViewHelper;
import com.fss.common.uiModule.nineoldandroids.animation.AnimatorSet;


/**
 * Created by lee on 2014/7/30.
 */
public abstract  class BaseEffects {
    public final int DURATION = 1 * 700;

    private long mDuration=DURATION ;

    private AnimatorSet mAnimatorSet;

    {
        mAnimatorSet = new AnimatorSet();
    }

    protected abstract void setupAnimation(View view);

    public void start(View view) {
        reset(view);
        setupAnimation(view);
        mAnimatorSet.start();
    }
    public void reset(View view) {
        ViewHelper.setAlpha(view, 1);
        ViewHelper.setScaleX(view, 1);
        ViewHelper.setScaleY(view, 1);
        ViewHelper.setTranslationX(view, 0);
        ViewHelper.setTranslationY(view, 0);
        ViewHelper.setRotation(view, 0);
        ViewHelper.setRotationY(view, 0);
        ViewHelper.setRotationX(view, 0);
        ViewHelper.setPivotX(view, view.getMeasuredWidth() / 2.0f);
        ViewHelper.setPivotY(view, view.getMeasuredHeight() / 2.0f);
    }


    public AnimatorSet getAnimatorSet() {
        return mAnimatorSet;
    }


}
