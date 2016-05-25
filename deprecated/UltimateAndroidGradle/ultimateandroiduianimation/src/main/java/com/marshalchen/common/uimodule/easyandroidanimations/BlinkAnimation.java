package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * This animation causes the view to fade in and fade out a customizable number
 * of times.
 * 
 * @author SiYao
 * 
 */
public class BlinkAnimation extends Animation {

	int numOfBlinks, blinkCount = 0;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation causes the view to fade in and fade out a customizable
	 * number of times.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public BlinkAnimation(View view) {
		this.view = view;
		numOfBlinks = 2;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		long singleBlinkDuration = duration / numOfBlinks / 2;
		if (singleBlinkDuration == 0)
			singleBlinkDuration = 1;
		ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, View.ALPHA, 0), fadeIn = ObjectAnimator
				.ofFloat(view, View.ALPHA, 1);
		final AnimatorSet blinkAnim = new AnimatorSet();
		blinkAnim.playSequentially(fadeOut, fadeIn);
		blinkAnim.setInterpolator(interpolator);
		blinkAnim.setDuration(singleBlinkDuration);
		blinkAnim.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				blinkCount++;
				if (blinkCount == numOfBlinks) {
					if (getListener() != null) {
						getListener().onAnimationEnd(BlinkAnimation.this);
					}
				} else {
					blinkAnim.start();
				}
			}
		});
		blinkAnim.start();
	}

	/**
	 * @return The number of blinks.
	 */
	public int getNumOfBlinks() {
		return numOfBlinks;
	}

	/**
	 * @param numOfBlinks
	 *            The number of blinks to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public BlinkAnimation setNumOfBlinks(int numOfBlinks) {
		this.numOfBlinks = numOfBlinks;
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
	public BlinkAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public BlinkAnimation setDuration(long duration) {
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
	public BlinkAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
