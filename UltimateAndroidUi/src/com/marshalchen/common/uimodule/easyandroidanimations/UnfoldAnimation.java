package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.marshalchen.common.uimodule.easyandroidanimations.FoldLayout.Orientation;

/**
 * This animation folds in the view for a customizable number of folds,
 * orientation and at a customizable anchor factor.
 * 
 * @author SiYao
 * 
 */
public class UnfoldAnimation extends Animation {

	private final int ANTIALIAS_PADDING = 1;

	int numOfFolds;
	Orientation orientation;
	float anchorFactor;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation folds in the view for a customizable number of folds,
	 * orientation and at a customizable anchor factor.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public UnfoldAnimation(View view) {
		this.view = view;
		numOfFolds = 1;
		orientation = Orientation.HORIZONTAL;
		anchorFactor = 0f;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		final ViewGroup parentView = (ViewGroup) view.getParent();
		final int positionView = parentView.indexOfChild(view);
		final FoldLayout mFoldLayout = new FoldLayout(view.getContext());
		mFoldLayout.setLayoutParams(new LayoutParams(view.getWidth(), view
				.getHeight()));
		mFoldLayout.setX(view.getLeft());
		mFoldLayout.setY(view.getTop());
		parentView.removeView(view);
		parentView.addView(mFoldLayout, positionView);
		mFoldLayout.addView(view);
		view.setPadding(ANTIALIAS_PADDING, ANTIALIAS_PADDING,
				ANTIALIAS_PADDING, ANTIALIAS_PADDING);

		mFoldLayout.setNumberOfFolds(numOfFolds);
		mFoldLayout.setOrientation(orientation);
		mFoldLayout.setAnchorFactor(anchorFactor);
		mFoldLayout.setFoldFactor(1);
		 mFoldLayout.setVisibility(View.INVISIBLE);

		final ObjectAnimator foldInAnim = ObjectAnimator.ofFloat(mFoldLayout,
				"foldFactor", 1, 0);
		foldInAnim.setDuration(duration);
		foldInAnim.setInterpolator(interpolator);
		foldInAnim.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(UnfoldAnimation.this);
				}
			}
		});
		foldInAnim.start();
		ObjectAnimator foldOutAnim = ObjectAnimator.ofFloat(mFoldLayout,
				"foldFactor", 1);
		foldOutAnim.setDuration(1);
		foldOutAnim.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				mFoldLayout.setVisibility(View.VISIBLE);
				view.setVisibility(View.VISIBLE);
				foldInAnim.start();
			}
		});
		foldOutAnim.start();
	}

	/**
	 * @return The number of folds.
	 */
	public int getNumOfFolds() {
		return numOfFolds;
	}

	/**
	 * @param numOfFolds
	 *            The number of folds to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public UnfoldAnimation setNumOfFolds(int numOfFolds) {
		this.numOfFolds = numOfFolds;
		return this;
	}

	/**
	 * The available orientations are <code>Orientation.HORIZONTAL</code> and
	 * <code>Orientation.VERTICAL</code>.
	 * 
	 * @return The orientation of the fold.
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * The available orientations are <code>Orientation.HORIZONTAL</code> and
	 * <code>Orientation.VERTICAL</code>.
	 * 
	 * @param orientation
	 *            The orientation of the fold to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public UnfoldAnimation setOrientation(Orientation orientation) {
		this.orientation = orientation;
		return this;
	}

	/**
	 * The available anchor factors range from 0 to 1. For example, in order to
	 * anchor the fold on the left, the anchor factor should be 0.
	 * 
	 * @return The anchor factor of the fold.
	 */
	public float getAnchorFactor() {
		return anchorFactor;
	}

	/**
	 * The available anchor factors range from 0 to 1. For example, in order to
	 * anchor the fold on the left, the anchor factor should be 0.
	 * 
	 * @param anchorFactor
	 *            The anchor factor of the fold to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public UnfoldAnimation setAnchorFactor(float anchorFactor) {
		this.anchorFactor = anchorFactor;
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
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public UnfoldAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public UnfoldAnimation setDuration(long duration) {
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
	public UnfoldAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
