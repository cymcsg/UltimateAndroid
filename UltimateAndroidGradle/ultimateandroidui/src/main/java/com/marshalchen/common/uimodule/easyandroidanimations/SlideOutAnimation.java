package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * This animation causes the view to slide out to the borders of the screen. On
 * animation end, the view is restored to its original state and is set to
 * <code>View.INVISIBLE</code>.
 * 
 * @author SiYao
 * 
 */
public class SlideOutAnimation extends Animation implements Combinable {

	int direction;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;
	ValueAnimator slideAnim;

	/**
	 * This animation causes the view to slide out to the borders of the screen.
	 * On animation end, the view is restored to its original state and is set
	 * to <code>View.INVISIBLE</code>.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public SlideOutAnimation(View view) {
		this.view = view;
		direction = DIRECTION_LEFT;
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
		ViewGroup parentView = (ViewGroup) view.getParent(), rootView = (ViewGroup) view
				.getRootView();
		while (!parentView.equals(rootView)) {
			parentView.setClipChildren(false);
			parentView = (ViewGroup) parentView.getParent();
		}
		rootView.setClipChildren(false);

		final int[] locationView = new int[2];
		view.getLocationOnScreen(locationView);

		switch (direction) {
		case DIRECTION_LEFT:
			slideAnim = ObjectAnimator.ofFloat(view, View.X, -locationView[0]
					- view.getWidth());
			break;
		case DIRECTION_RIGHT:
			slideAnim = ObjectAnimator.ofFloat(view, View.X,
					rootView.getRight());
			break;
		case DIRECTION_UP:
			slideAnim = ObjectAnimator.ofFloat(view, View.Y, -locationView[1]
					- view.getHeight());
			break;
		case DIRECTION_DOWN:
			slideAnim = ObjectAnimator.ofFloat(view, View.Y,
					rootView.getBottom());
			break;
		default:
			break;
		}

		AnimatorSet slideSet = new AnimatorSet();
		slideSet.play(slideAnim);
		slideSet.setInterpolator(interpolator);
		slideSet.setDuration(duration);
		slideSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.INVISIBLE);
				slideAnim.reverse();
				if (getListener() != null) {
					getListener().onAnimationEnd(SlideOutAnimation.this);
				}
			}
		});
		return slideSet;
	}

	/**
	 * The available directions to slide in from are <code>DIRECTION_LEFT</code>
	 * , <code>DIRECTION_RIGHT</code>, <code>DIRECTION_TOP</code> and
	 * <code>DIRECTION_BOTTOM</code>.
	 * 
	 * @return The direction to slide the view out to.
	 * @see Animation
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * The available directions to slide in from are <code>DIRECTION_LEFT</code>
	 * , <code>DIRECTION_RIGHT</code>, <code>DIRECTION_TOP</code> and
	 * <code>DIRECTION_BOTTOM</code>.
	 * 
	 * @param direction
	 *            The direction to set to slide the view out to.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 * @see Animation
	 */
	public SlideOutAnimation setDirection(int direction) {
		this.direction = direction;
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
	public SlideOutAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public SlideOutAnimation setDuration(long duration) {
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
	public SlideOutAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
