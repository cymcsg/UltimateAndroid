package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * This animation fades the view in by animating its alpha property from 0 to 1.
 * 
 * @author SiYao
 * 
 */
public class FadeInAnimation extends Animation implements Combinable {

	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation fades the view in by animating its alpha property from 0
	 * to 1.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public FadeInAnimation(View view) {
		this.view = view;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		getAnimatorSet().start();
	}

	@Override
	public AnimatorSet getAnimatorSet() {
		view.setAlpha(0f);
		view.setVisibility(View.VISIBLE);

		AnimatorSet fadeSet = new AnimatorSet();
		fadeSet.play(ObjectAnimator.ofFloat(view, View.ALPHA, 1f));
		fadeSet.setInterpolator(interpolator);
		fadeSet.setDuration(duration);
		fadeSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(FadeInAnimation.this);
				}
			}
		});
		return fadeSet;
	}

	/**
	 * @return The interpolator of the entire animation.
	 */
	public TimeInterpolator getInterpolator() {
		return interpolator;
	}

	/**
	 * @param interpolator
	 *            The interpolator of the entire animation to set.
	 */
	public FadeInAnimation setInterpolator(TimeInterpolator interpolator) {
		this.interpolator = interpolator;
		return this;
	}

	/**
	 * @return The duration of the entire animation.
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @return The duration of the entire animation to set.
	 */
	public FadeInAnimation setDuration(long duration) {
		this.duration = duration;
		return this;
	}

	/**
	 * @return The listener for the end of the animation.
	 */
	public AnimationListener getListener() {
		return listener;
	}

	/**
	 * @return The listener to set for the end of the animation.
	 */
	public FadeInAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
