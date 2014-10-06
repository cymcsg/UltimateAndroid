package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * This animation causes the view to bounce by translating up and down for a
 * customizable number of times before returning to its original position.
 * 
 * @author SiYao
 * 
 */
public class BounceAnimation extends Animation {

	float bounceDistance;
	int numOfBounces, bounceCount = 0;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation causes the view to bounce by translating up and down for a
	 * customizable number of times before returning to its original position.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public BounceAnimation(View view) {
		this.view = view;
		bounceDistance = 20;
		numOfBounces = 2;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		long singleBounceDuration = duration / numOfBounces / 4;
		if (singleBounceDuration == 0)
			singleBounceDuration = 1;
		final AnimatorSet bounceAnim = new AnimatorSet();
		bounceAnim.playSequentially(ObjectAnimator.ofFloat(view,
				View.TRANSLATION_Y, bounceDistance), ObjectAnimator.ofFloat(
				view, View.TRANSLATION_Y, -bounceDistance), ObjectAnimator
				.ofFloat(view, View.TRANSLATION_Y, bounceDistance),
				ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0));
		bounceAnim.setInterpolator(interpolator);
		bounceAnim.setDuration(singleBounceDuration);

		ViewGroup parentView = (ViewGroup) view.getParent(), rootView = (ViewGroup) view
				.getRootView();
		while (!parentView.equals(rootView)) {
			parentView.setClipChildren(false);
			parentView = (ViewGroup) parentView.getParent();
		}
		rootView.setClipChildren(false);
		bounceAnim.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				bounceCount++;
				if (bounceCount == numOfBounces) {
					if (getListener() != null) {
						getListener().onAnimationEnd(BounceAnimation.this);
					}
				} else {
					bounceAnim.start();
				}
			}
		});
		bounceAnim.start();
	}

	/**
	 * @return The maximum bounce distance.
	 */
	public float getBounceDistance() {
		return bounceDistance;
	}

	/**
	 * @param bounceDistance
	 *            The maximum bounce distance to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public BounceAnimation setBounceDistance(float bounceDistance) {
		this.bounceDistance = bounceDistance;
		return this;
	}

	/**
	 * @return The number of bounces.
	 */
	public int getNumOfBounces() {
		return numOfBounces;
	}

	/**
	 * @param numOfBounces
	 *            The number of bounces to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public BounceAnimation setNumOfBounces(int numOfBounces) {
		this.numOfBounces = numOfBounces;
		return this;
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
	public BounceAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public BounceAnimation setDuration(long duration) {
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
	public BounceAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
