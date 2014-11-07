package com.marshalchen.common.uimodule.activityanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.widget.FrameLayout;

public abstract class AnimatedDoorActivity extends Activity {

    private AnimatedDoorLayout mAnimated;
    protected int mDoorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId());

        FrameLayout activityRoot = (FrameLayout) findViewById(android.R.id.content);
        View parent = activityRoot.getChildAt(0);

        // better way ?
        mAnimated = new AnimatedDoorLayout(this);
        activityRoot.removeView(parent);
        activityRoot.addView(mAnimated, parent.getLayoutParams());
        mAnimated.addView(parent);

        mDoorType = getIntent().getIntExtra("door_type", AnimatedDoorLayout.HORIZONTAL_DOOR);
        mAnimated.setDoorType(mDoorType);

        ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimated, ANIMATED_DOOR_LAYOUT_FLOAT_PROPERTY, 1).setDuration(600);
        animator.start();
    }

    protected abstract int layoutResId();

    @Override
    public void onBackPressed() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimated, ANIMATED_DOOR_LAYOUT_FLOAT_PROPERTY, 0).setDuration(600);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }
        });
        animator.start();
    }


    private static final Property<AnimatedDoorLayout, Float> ANIMATED_DOOR_LAYOUT_FLOAT_PROPERTY =
            new Property<AnimatedDoorLayout, Float>(Float.class, "ANIMATED_DOOR_LAYOUT_FLOAT_PROPERTY") {

                @Override
                public void set(AnimatedDoorLayout layout, Float value) {
                    layout.setProgress(value);
                }

                @Override
                public Float get(AnimatedDoorLayout layout) {
                    return layout.getProgress();
                }
            };

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
