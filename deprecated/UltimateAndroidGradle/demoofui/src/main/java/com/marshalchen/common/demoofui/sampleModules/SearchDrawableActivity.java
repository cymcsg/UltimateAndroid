package com.marshalchen.common.demoofui.sampleModules;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.SearchDrawable;


public class SearchDrawableActivity extends ActionBarActivity implements View.OnClickListener {

    private long START_ANIMATION_DURATION = 600;
    private Animation mStartAnimation;
    private SearchDrawable searchDrawable;
    private ImageView imageView;
    boolean back = true;
    TextView textView;
    private Interpolator interp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_drawable_activity);
        imageView = (ImageView) findViewById(R.id.search);
        textView = (TextView) findViewById(R.id.text);
        searchDrawable = new SearchDrawable();
        imageView.setImageDrawable(searchDrawable);
        //interp = AnimationUtils.loadInterpolator(this, android.R.interpolator.Animator_interpolator);
        startUpAnimation();
        imageView.setOnClickListener(this);
    }

    private void startUpAnimation() {
        mStartAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                if (back) {
                    searchDrawable.setPhase(1 - interpolatedTime);
                    textView.setAlpha(0f);
                    textView.setVisibility(View.GONE);
                } else {
                    searchDrawable.setPhase(interpolatedTime);
                    textView.animate().alpha(1f).setDuration(100).setInterpolator(new AccelerateDecelerateInterpolator());
                    textView.setVisibility(View.VISIBLE);
                }
            }
        };
        mStartAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mStartAnimation.setDuration(START_ANIMATION_DURATION);
    }

    @Override
    public void onClick(View v) {
        imageView.startAnimation(mStartAnimation);
        back = !back;
    }
}
