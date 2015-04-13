package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * This animation hides the view by scaling its Y property to mimic the
 * "pulling of blinds". On animation end, the view is restored to its original
 * state and is set to <code>View.INVISIBLE</code>.
 * 
 * @author Phu
 * 
 */
public class BlindAnimation extends Animation {

	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation hides the view by scaling its Y property to mimic the
	 * "pulling of blinds". On animation end, the view is restored to its
	 * original state and is set to <code>View.INVISIBLE</code>.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public BlindAnimation(View view) {
		this.view = view;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		final ViewGroup parent = (ViewGroup) view.getParent(), animationLayout = new FrameLayout(view.getContext());
		final int positionView = parent.indexOfChild(view);
		animationLayout.setLayoutParams(view.getLayoutParams());
		parent.removeView(view);
		animationLayout.addView(view);
		parent.addView(animationLayout, positionView);

		final float originalScaleY = view.getScaleY();
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(animationLayout,
				View.SCALE_Y, 0f), scaleY_child = ObjectAnimator.ofFloat(view,
				View.SCALE_Y, 2.5f);
		
		animationLayout.setPivotX(1f);
		animationLayout.setPivotY(1f);
		view.setPivotX(1f);
		view.setPivotY(1f);
		
		AnimatorSet blindAnimationSet = new AnimatorSet();
		blindAnimationSet.playTogether(scaleY, scaleY_child);
		blindAnimationSet.setInterpolator(interpolator);
		blindAnimationSet.setDuration(duration / 2);
		blindAnimationSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.INVISIBLE);
				view.setScaleY(originalScaleY);
				animationLayout.removeAllViews();
				parent.removeView(animationLayout);
				parent.addView(view, positionView);
				if (getListener() != null) {
					getListener().onAnimationEnd(BlindAnimation.this);
				}
			}
		});
		blindAnimationSet.start();
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
	public BlindAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public BlindAnimation setDuration(long duration) {
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
	public BlindAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
