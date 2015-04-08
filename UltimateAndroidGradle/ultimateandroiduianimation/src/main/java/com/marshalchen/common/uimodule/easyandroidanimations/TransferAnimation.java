package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * This animation transfers the view to another view provided by the user
 * through scaling and translation. The view is scaled to the same size and is
 * translated to the same position as the destination view.
 * 
 * @author SiYao
 * 
 */
public class TransferAnimation extends Animation {

	View destinationView;
	int transX, transY;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;
	ViewGroup parentView;

	/**
	 * This animation transfers the view to another view provided by the user
	 * through scaling and translation. The view is scaled to the same size and
	 * is translated to the same position as the destination view.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public TransferAnimation(View view) {
		this.view = view;
		destinationView = null;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		parentView = (ViewGroup) view.getParent();
		final ViewGroup rootView = (ViewGroup) view.getRootView();
		while (!parentView.equals(rootView)) {
			parentView.setClipChildren(false);
			parentView = (ViewGroup) parentView.getParent();
		}
		rootView.setClipChildren(false);

		final float scaleX = (float) destinationView.getWidth()
				/ ((float) view.getWidth()), scaleY = (float) destinationView
				.getHeight() / ((float) view.getHeight());
		int[] locationDest = new int[2], locationView = new int[2];
		view.getLocationOnScreen(locationView);
		destinationView.getLocationOnScreen(locationDest);
		transX = locationDest[0] - locationView[0];
		transY = locationDest[1] - locationView[1];
		transX = transX - view.getWidth() / 2 + destinationView.getWidth() / 2;
		transY = transY - view.getHeight() / 2 + destinationView.getHeight()
				/ 2;

		view.animate().scaleX(scaleX).scaleY(scaleY).translationX(transX)
				.translationY(transY).setInterpolator(interpolator)
				.setDuration(duration)
				.setListener(new AnimatorListenerAdapter() {

					@Override
					public void onAnimationEnd(Animator animation) {
						if (getListener() != null) {
							getListener()
									.onAnimationEnd(TransferAnimation.this);
						}
					}
				});
	}

	/**
	 * @return The destination view to transfer the original view to.
	 */
	public View getDestinationView() {
		return destinationView;
	}

	/**
	 * @param destinationView
	 *            The destination view to set to transfer the original view to.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public TransferAnimation setDestinationView(View destinationView) {
		this.destinationView = destinationView;
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
	public TransferAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public TransferAnimation setDuration(long duration) {
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
	public TransferAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}
}
