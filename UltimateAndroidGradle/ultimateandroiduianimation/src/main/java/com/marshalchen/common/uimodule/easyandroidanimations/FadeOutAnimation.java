package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * This animation fades the view out by animating its alpha property to 0. On
 * animation end, the view is restored to its original state and is set to
 * <code>View.INVISIBLE</code>.
 * 
 * @author SiYao
 * 
 */
public class FadeOutAnimation extends Animation implements Combinable {

	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation fades the view out by animating its alpha property to 0.
	 * On animation end, the view is restored to its original state and is set
	 * to <code>View.INVISIBLE</code>.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public FadeOutAnimation(View view) {
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
		final float originalAlpha = view.getAlpha();
		AnimatorSet fadeSet = new AnimatorSet();
		fadeSet.play(ObjectAnimator.ofFloat(view, View.ALPHA, 0f));
		fadeSet.setInterpolator(interpolator);
		fadeSet.setDuration(duration);
		fadeSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.INVISIBLE);
				view.setAlpha(originalAlpha);
				if (getListener() != null) {
					getListener().onAnimationEnd(FadeOutAnimation.this);
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
	public FadeOutAnimation setInterpolator(TimeInterpolator interpolator) {
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
	 * @param duration
	 *            The duration of the entire animation to set.
	 */
	public FadeOutAnimation setDuration(long duration) {
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
	 * @param listener
	 *            The listener to set for the end of the animation.
	 */
	public FadeOutAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
