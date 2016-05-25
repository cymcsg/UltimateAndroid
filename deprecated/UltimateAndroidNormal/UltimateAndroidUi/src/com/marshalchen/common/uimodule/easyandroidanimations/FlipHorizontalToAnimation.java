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
 * This animation causes the view to flip horizontally to reveal another
 * user-provided view at the back of the original view. On animation end, the
 * original view is restored to its original state and is set to
 * <code>View.INVISIBLE</code>.
 * 
 * @author SiYao
 * 
 */
public class FlipHorizontalToAnimation extends Animation {

	public static final int PIVOT_CENTER = 0, PIVOT_LEFT = 1, PIVOT_RIGHT = 2;

	View flipToView;
	int pivot, direction;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation causes the view to flip horizontally to reveal another
	 * user-provided view at the back of the original view. On animation end,
	 * the original view is restored to its original state and is set to
	 * <code>View.INVISIBLE</code>.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public FlipHorizontalToAnimation(View view) {
		this.view = view;
		flipToView = null;
		pivot = PIVOT_CENTER;
		direction = DIRECTION_RIGHT;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		ViewGroup parentView = (ViewGroup) view.getParent(), rootView = (ViewGroup) view
				.getRootView();

		float pivotX, pivotY, flipAngle = 270f, viewWidth = view.getWidth(), viewHeight = view
				.getHeight();
		final float originalRotationY = view.getRotationY();
		switch (pivot) {
		case PIVOT_LEFT:
			pivotX = 0f;
			pivotY = viewHeight / 2;
			break;
		case PIVOT_RIGHT:
			pivotX = viewWidth;
			pivotY = viewHeight / 2;
			break;
		default:
			pivotX = viewWidth / 2;
			pivotY = viewHeight / 2;
			flipAngle = 90f;
			break;
		}
		view.setPivotX(pivotX);
		view.setPivotY(pivotY);
		flipToView.setLayoutParams(view.getLayoutParams());
		flipToView.setLeft(view.getLeft());
		flipToView.setTop(view.getTop());
		flipToView.setPivotX(pivotX);
		flipToView.setPivotY(pivotY);
		flipToView.setVisibility(View.VISIBLE);

		while (parentView != rootView) {
			parentView.setClipChildren(false);
			parentView = (ViewGroup) parentView.getParent();
		}
		rootView.setClipChildren(false);

		AnimatorSet flipToAnim = new AnimatorSet();
		if (direction == DIRECTION_RIGHT) {
			flipToView.setRotationY(270f);
			flipToAnim.playSequentially(ObjectAnimator.ofFloat(view,
					View.ROTATION_Y, 0f, flipAngle), ObjectAnimator.ofFloat(
					flipToView, View.ROTATION_Y, 270f, 360f));
		} else {
			flipToView.setRotationY(-270f);
			flipToAnim.playSequentially(ObjectAnimator.ofFloat(view,
					View.ROTATION_Y, 0f, -flipAngle), ObjectAnimator.ofFloat(
					flipToView, View.ROTATION_Y, -270f, -360f));
		}
		flipToAnim.setInterpolator(interpolator);
		flipToAnim.setDuration(duration / 2);
		flipToAnim.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.INVISIBLE);
				view.setRotationY(originalRotationY);
				if (getListener() != null) {
					getListener()
							.onAnimationEnd(FlipHorizontalToAnimation.this);
				}
			}
		});
		flipToAnim.start();
	}

	/**
	 * @return The view to be revealed after flipping the original view.
	 */
	public View getFlipToView() {
		return flipToView;
	}

	/**
	 * @param flipToView
	 *            The view to set to be revealed after flipping the original
	 *            view.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public FlipHorizontalToAnimation setFlipToView(View flipToView) {
		this.flipToView = flipToView;
		return this;
	}

	/**
	 * The available pivot points to flip are <code>PIVOT_CENTER</code>,
	 * <code>PIVOT_LEFT</code> and <code>PIVOT_RIGHT</code>.
	 * 
	 * @return The pivot point for flipping.
	 */
	public int getPivot() {
		return pivot;
	}

	/**
	 * The available pivot points to flip are <code>PIVOT_CENTER</code>,
	 * <code>PIVOT_LEFT</code> and <code>PIVOT_RIGHT</code>.
	 * 
	 * @param pivot
	 *            The pivot point to set for flipping.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public FlipHorizontalToAnimation setPivot(int pivot) {
		this.pivot = pivot;
		return this;
	}

	/**
	 * The available flip directions are <code>DIRECTION_LEFT</code> and
	 * <code>DIRECTION_RIGHT</code>.
	 * 
	 * @return The direction of the flip.
	 * @see Animation
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * The available flip directions are <code>DIRECTION_LEFT</code> and
	 * <code>DIRECTION_RIGHT</code>.
	 * 
	 * @param direction
	 *            The direction of the flip to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 * @see Animation
	 */
	public FlipHorizontalToAnimation setDirection(int direction) {
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
	public FlipHorizontalToAnimation setInterpolator(
			TimeInterpolator interpolator) {
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
	public FlipHorizontalToAnimation setDuration(long duration) {
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
	public FlipHorizontalToAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
