package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * This animation scales out the view from 1 to 0. On animation end, the view is
 * restored to its original state and is set to <code>View.INVISIBLE</code>.
 * 
 * @author SiYao
 * 
 */
public class ScaleOutAnimation extends Animation implements Combinable {

	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation scales out the view from 1 to 0. On animation end, the
	 * view is restored to its original state and is set to
	 * <code>View.INVISIBLE</code>.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public ScaleOutAnimation(View view) {
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
		final float originalScaleX = view.getScaleX(), originalScaleY = view
				.getScaleY();
		AnimatorSet scaleSet = new AnimatorSet();
		scaleSet.playTogether(ObjectAnimator.ofFloat(view, View.SCALE_X, 0f),
				ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f));
		scaleSet.setInterpolator(interpolator);
		scaleSet.setDuration(duration);
		scaleSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.INVISIBLE);
				view.setScaleX(originalScaleX);
				view.setScaleY(originalScaleY);
				if (getListener() != null) {
					getListener().onAnimationEnd(ScaleOutAnimation.this);
				}
			}
		});
		return scaleSet;
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
	public ScaleOutAnimation setInterpolator(TimeInterpolator interpolator) {
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
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public ScaleOutAnimation setDuration(long duration) {
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
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public ScaleOutAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
