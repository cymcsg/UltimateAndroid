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
 * This animation causes the view to slide in from the borders of the screen.
 * 
 * @author SiYao
 * 
 */
public class SlideInAnimation extends Animation implements Combinable {

	int direction;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation causes the view to slide in from the borders of the
	 * screen.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public SlideInAnimation(View view) {
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
		int[] locationView = new int[2];
		view.getLocationOnScreen(locationView);

		ObjectAnimator slideAnim = null;
		switch (direction) {
		case DIRECTION_LEFT:
			slideAnim = ObjectAnimator.ofFloat(view, View.X, -locationView[0]
					- view.getWidth(), view.getX());
			break;
		case DIRECTION_RIGHT:
			slideAnim = ObjectAnimator.ofFloat(view, View.X,
					rootView.getRight(), view.getX());
			break;
		case DIRECTION_UP:
			slideAnim = ObjectAnimator.ofFloat(view, View.Y, -locationView[1]
					- view.getHeight(), view.getY());
			break;
		case DIRECTION_DOWN:
			slideAnim = ObjectAnimator.ofFloat(view, View.Y,
					rootView.getBottom(), view.getY());
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
			public void onAnimationStart(Animator animation) {
				view.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(SlideInAnimation.this);
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
	 * @return The direction to slide the view in from.
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
	 *            The direction to set to slide the view in from.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 * @see Animation
	 */
	public SlideInAnimation setDirection(int direction) {
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
	public SlideInAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public SlideInAnimation setDuration(long duration) {
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
	public SlideInAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
