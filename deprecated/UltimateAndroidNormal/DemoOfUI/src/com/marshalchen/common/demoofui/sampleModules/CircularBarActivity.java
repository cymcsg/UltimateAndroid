package com.marshalchen.common.demoofui.sampleModules;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.commonUtils.basicUtils.HandlerUtils;
import com.marshalchen.common.uimodule.nineoldandroids.animation.Animator;
import com.marshalchen.common.uimodule.nineoldandroids.animation.ObjectAnimator;
import com.marshalchen.common.uimodule.nineoldandroids.animation.ValueAnimator;
import com.marshalchen.common.ui.HoloCircularProgressBar;
import com.marshalchen.common.ui.HomeasUpActionbarActivity;
import com.marshalchen.common.demoofui.R;

/**
 * Created by cym on 14-6-26.
 */
public class CircularBarActivity extends HomeasUpActionbarActivity {
    //    @InjectView(R.id.photoView)
//    PhotoView photoView;
    @InjectView(R.id.circularProgressBar)
    HoloCircularProgressBar circularProgressBar;
    private ObjectAnimator mProgressBarAnimator;
    protected boolean mAnimationHasEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circular_progress_bar_activity);
        ButterKnife.inject(this);
        if (mProgressBarAnimator != null) {
            mProgressBarAnimator.cancel();
        }
        animate(circularProgressBar, null, 0f, 1000);
        circularProgressBar.setMarkerProgress(0f);
        HandlerUtils.sendMessageHandler(tooneAnimateHandler, 0, 500);
        HandlerUtils.sendMessageHandler(autoAnimateHandler, 0, 3500);
    }

    Handler tooneAnimateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            animate(circularProgressBar, null, 1f, 1000);
            circularProgressBar.setMarkerProgress(1f);
            circularProgressBar.setProgressColor(Color.CYAN);
        }
    };

    Handler autoAnimateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            animate(circularProgressBar, new Animator.AnimatorListener() {

                @Override
                public void onAnimationCancel(final Animator animation) {
                    animation.end();
                }

                @Override
                public void onAnimationEnd(final Animator animation) {
                    if (!mAnimationHasEnded) {
                        animate(circularProgressBar, this);
                    } else {
                        mAnimationHasEnded = false;
                    }
                }

                @Override
                public void onAnimationRepeat(final Animator animation) {
                }

                @Override
                public void onAnimationStart(final Animator animation) {
                }
            });
        }
    };

    private void animate(final HoloCircularProgressBar progressBar, final Animator.AnimatorListener listener) {
        final float progress = (float) (Math.random() * 2);
        int duration = 3000;
        animate(progressBar, listener, progress, duration);
    }

    private void animate(final HoloCircularProgressBar progressBar, final Animator.AnimatorListener listener,
                         final float progress, final int duration) {

        mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        mProgressBarAnimator.setDuration(duration);

        mProgressBarAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            mProgressBarAnimator.addListener(listener);
        }
        mProgressBarAnimator.reverse();
        mProgressBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
            }
        });
        progressBar.setMarkerProgress(progress);
        mProgressBarAnimator.start();
    }

}
