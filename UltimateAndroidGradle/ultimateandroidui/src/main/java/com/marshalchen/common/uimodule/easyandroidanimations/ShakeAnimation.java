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
 * This animation causes the view to shake from left to right for a customizable
 * number of times before returning to its original position.
 * 
 * @author SiYao
 * 
 */
public class ShakeAnimation extends Animation {

	float shakeDistance;
	int numOfShakes, shakeCount = 0;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation causes the view to shake from left to right for a
	 * customizable number of times before returning to its original position.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public ShakeAnimation(View view) {
		this.view = view;
		shakeDistance = 20;
		numOfShakes = 2;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		long singleShakeDuration = duration / numOfShakes / 2;
		if (singleShakeDuration == 0)
			singleShakeDuration = 1;
		final AnimatorSet shakeAnim = new AnimatorSet();
		shakeAnim
				.playSequentially(ObjectAnimator.ofFloat(view,
						View.TRANSLATION_X, shakeDistance), ObjectAnimator
						.ofFloat(view, View.TRANSLATION_X, -shakeDistance),
						ObjectAnimator.ofFloat(view, View.TRANSLATION_X,
								shakeDistance), ObjectAnimator.ofFloat(view,
								View.TRANSLATION_X, 0));
		shakeAnim.setInterpolator(interpolator);
		shakeAnim.setDuration(singleShakeDuration);

		ViewGroup parentView = (ViewGroup) view.getParent(), rootView = (ViewGroup) view
				.getRootView();
		while (!parentView.equals(rootView)) {
			parentView.setClipChildren(false);
			parentView = (ViewGroup) parentView.getParent();
		}
		rootView.setClipChildren(false);
		shakeAnim.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				shakeCount++;
				if (shakeCount == numOfShakes) {
					if (getListener() != null) {
						getListener().onAnimationEnd(ShakeAnimation.this);
					}
				} else {
					shakeAnim.start();
				}
			}
		});
		shakeAnim.start();
	}

	/**
	 * @return The maximum shake distance.
	 */
	public float getShakeDistance() {
		return shakeDistance;
	}

	/**
	 * @param shakeDistance
	 *            The maximum shake distance to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public ShakeAnimation setShakeDistance(float shakeDistance) {
		this.shakeDistance = shakeDistance;
		return this;
	}

	/**
	 * @return The number of shakes.
	 */
	public int getNumOfShakes() {
		return numOfShakes;
	}

	/**
	 * @param numOfShakes
	 *            The number of shakes to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public ShakeAnimation setNumOfShakes(int numOfShakes) {
		this.numOfShakes = numOfShakes;
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
	public ShakeAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public ShakeAnimation setDuration(long duration) {
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
	public ShakeAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
