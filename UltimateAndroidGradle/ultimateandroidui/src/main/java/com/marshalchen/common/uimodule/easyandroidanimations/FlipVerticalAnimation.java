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
 * This animation causes the view to flip vertically by a customizable number of
 * degrees and at a customizable pivot point.
 * 
 * @author SiYao
 * 
 */
public class FlipVerticalAnimation extends Animation implements Combinable {

	public static final int PIVOT_CENTER = 0, PIVOT_TOP = 1, PIVOT_BOTTOM = 2;

	float degrees;
	int pivot;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation causes the view to flip vertically by a customizable
	 * number of degrees and at a customizable pivot point.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public FlipVerticalAnimation(View view) {
		this.view = view;
		degrees = 360;
		pivot = PIVOT_CENTER;
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
		while (parentView != rootView) {
			parentView.setClipChildren(false);
			parentView = (ViewGroup) parentView.getParent();
		}
		rootView.setClipChildren(false);

		float pivotX, pivotY, viewWidth = view.getWidth(), viewHeight = view
				.getHeight();
		switch (pivot) {
		case PIVOT_TOP:
			pivotX = viewWidth / 2;
			pivotY = 0f;
			break;
		case PIVOT_BOTTOM:
			pivotX = viewWidth / 2;
			pivotY = viewHeight;
			break;
		default:
			pivotX = viewWidth / 2;
			pivotY = viewHeight / 2;
			break;
		}
		view.setPivotX(pivotX);
		view.setPivotY(pivotY);

		AnimatorSet flipSet = new AnimatorSet();
		flipSet.play(ObjectAnimator.ofFloat(view, View.ROTATION_X,
				view.getRotationX() + degrees));
		flipSet.setInterpolator(interpolator);
		flipSet.setDuration(duration);
		flipSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(FlipVerticalAnimation.this);
				}
			}
		});
		return flipSet;
	}

	/**
	 * @return The number of degrees to flip by.
	 */
	public float getDegrees() {
		return degrees;
	}

	/**
	 * In order to flip down, the number of degrees should be negative and vice
	 * versa.
	 * 
	 * @param degrees
	 *            The number of degrees to set to flip by.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public FlipVerticalAnimation setDegrees(float degrees) {
		this.degrees = degrees;
		return this;
	}

	/**
	 * The available pivot points are <code>PIVOT_CENTER</code>,
	 * <code>PIVOT_TOP</code> and <code>PIVOT_BOTTOM</code>.
	 * 
	 * @return The pivot point for flipping.
	 */
	public int getPivot() {
		return pivot;
	}

	/**
	 * The available pivot points are <code>PIVOT_CENTER</code>,
	 * <code>PIVOT_TOP</code> and <code>PIVOT_BOTTOM</code>.
	 * 
	 * @param pivot
	 *            The pivot point to set for flipping.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public FlipVerticalAnimation setPivot(int pivot) {
		this.pivot = pivot;
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
	public FlipVerticalAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public FlipVerticalAnimation setDuration(long duration) {
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
	public FlipVerticalAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
