package com.marshalchen.common.uimodule.easyandroidanimations;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;

/**
 * This class allows multiple Combinable objects to be animated in parallel.
 * 
 * @author SiYao
 * 
 */
public class ParallelAnimator extends Animation {

	ArrayList<Combinable> combinableList;
	TimeInterpolator interpolator;
	long duration;
	ParallelAnimatorListener listener;

	/**
	 * This class allows multiple Combinable objects to be animated in parallel.
	 */
	public ParallelAnimator() {
		interpolator = null;
		duration = 0;
		combinableList = new ArrayList<Combinable>();
		listener = null;
	}

	/**
	 * This method adds this Combinable object to an ArrayList.
	 * 
	 * @param combinable
	 *            The Animation object that implements the {@link Combinable}
	 *            interface and is allowed to animate with other animations.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public ParallelAnimator add(Combinable combinable) {
		combinableList.add(combinable);
		return this;
	}

	/**
	 * This method gets the ArrayList, sets any parameters if needed and plays
	 * all animations in parallel.
	 */
	@Override
	public void animate() {
		ArrayList<Animator> animatorList = new ArrayList<Animator>();
		for (int i = 0; i < combinableList.size(); i++) {
			if (duration > 0) {
				combinableList.get(i).setDuration(duration);
			}
			animatorList.add(combinableList.get(i).getAnimatorSet());
		}

		AnimatorSet parallelSet = new AnimatorSet();
		parallelSet.playTogether(animatorList);

		if (interpolator != null) {
			parallelSet.setInterpolator(interpolator);
		}

		parallelSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(ParallelAnimator.this);
				}
			}
		});
		parallelSet.start();
	}

	/**
	 * @return The interpolator of the entire parallel animation.
	 */
	public TimeInterpolator getInterpolator() {
		return interpolator;
	}

	/**
	 * This method overrides the <code>setInterpolator()</code> methods of all
	 * the parallel animations.
	 * 
	 * @param interpolator
	 *            The interpolator of the entire parallel animation to set.
	 */
	public ParallelAnimator setInterpolator(TimeInterpolator interpolator) {
		this.interpolator = interpolator;
		return this;
	}

	/**
	 * @return The duration of the entire parallel animation.
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * This method overrides the <code>setDuration()</code> methods of all the
	 * parallel animations.
	 * 
	 * @param duration
	 *            The duration of the entire parallel animation to set.
	 */
	public ParallelAnimator setDuration(long duration) {
		this.duration = duration;
		return this;
	}

	/**
	 * @return The listener for the end of the parallel animation.
	 */
	public ParallelAnimatorListener getListener() {
		return listener;
	}

	/**
	 * @param listener
	 *            The listener to set for the end of the parallel animation.
	 */
	public ParallelAnimator setListener(ParallelAnimatorListener listener) {
		this.listener = listener;
		return this;
	}

}
