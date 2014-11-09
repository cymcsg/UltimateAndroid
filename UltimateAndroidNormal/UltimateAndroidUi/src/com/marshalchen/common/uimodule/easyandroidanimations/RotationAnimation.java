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
 * This animation rotates the view by a customizable number of degrees and at a
 * customizable pivot point.
 * 
 * @author SiYao
 * 
 */
public class RotationAnimation extends Animation implements Combinable {

	public static final int PIVOT_CENTER = 0, PIVOT_TOP_LEFT = 1,
			PIVOT_TOP_RIGHT = 2, PIVOT_BOTTOM_LEFT = 3, PIVOT_BOTTOM_RIGHT = 4;

	float degrees;
	int pivot;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation rotates the view by a customizable number of degrees and
	 * at a customizable pivot point.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public RotationAnimation(View view) {
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
		case PIVOT_TOP_LEFT:
			pivotX = 1f;
			pivotY = 1f;
			break;
		case PIVOT_TOP_RIGHT:
			pivotX = viewWidth;
			pivotY = 1f;
			break;
		case PIVOT_BOTTOM_LEFT:
			pivotX = 1f;
			pivotY = viewHeight;
			break;
		case PIVOT_BOTTOM_RIGHT:
			pivotX = viewWidth;
			pivotY = viewHeight;
			break;
		default:
			pivotX = viewWidth / 2;
			pivotY = viewHeight / 2;
			break;
		}
		view.setPivotX(pivotX);
		view.setPivotY(pivotY);

		AnimatorSet rotationSet = new AnimatorSet();
		rotationSet.play(ObjectAnimator.ofFloat(view, View.ROTATION,
				view.getRotation() + degrees));
		rotationSet.setInterpolator(interpolator);
		rotationSet.setDuration(duration);
		rotationSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(RotationAnimation.this);
				}
			}
		});
		return rotationSet;
	}

	/**
	 * @return The number of degrees to rotate by.
	 */
	public float getDegrees() {
		return degrees;
	}

	/**
	 * In order to rotate anti-clockwise, the number of degrees should be
	 * negative.
	 * 
	 * @param degrees
	 *            The number of degrees to set to rotate by.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public RotationAnimation setDegrees(float degrees) {
		this.degrees = degrees;
		return this;
	}

	/**
	 * The available pivot points are <code>PIVOT_CENTER</code>,
	 * <code>PIVOT_TOP_LEFT</code>, <code>PIVOT_TOP_RIGHT</code>,
	 * <code>PIVOT_BOTTOM_LEFT</code> and <code>PIVOT_BOTTOM_RIGHT</code>.
	 * 
	 * @return The pivot point for rotation.
	 */
	public int getPivot() {
		return pivot;
	}

	/**
	 * The available pivot points are <code>PIVOT_CENTER</code>,
	 * <code>PIVOT_TOP_LEFT</code>, <code>PIVOT_TOP_RIGHT</code>,
	 * <code>PIVOT_BOTTOM_LEFT</code> and <code>PIVOT_BOTTOM_RIGHT</code>.
	 * 
	 * @param pivot
	 *            The pivot point to set for rotation.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public RotationAnimation setPivot(int pivot) {
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
	public RotationAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public RotationAnimation setDuration(long duration) {
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
	public RotationAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
