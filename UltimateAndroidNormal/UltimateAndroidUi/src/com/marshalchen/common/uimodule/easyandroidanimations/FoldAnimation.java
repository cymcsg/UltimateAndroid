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
 * This animation folds out the view for a customizable number of folds,
 * orientation and at a customizable anchor factor. On animation end, the view
 * is restored to its original state and is set to <code>View.INVISIBLE</code>.
 * 
 * @author SiYao
 * 
 */
public class FoldAnimation extends Animation {

	private final int ANTIALIAS_PADDING = 1;

	int numOfFolds;
	Orientation orientation;
	float anchorFactor;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation folds out the view for a customizable number of folds,
	 * orientation and at a customizable anchor factor. On animation end, the
	 * view is restored to its original state and is set to
	 * <code>View.INVISIBLE</code>.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public FoldAnimation(View view) {
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

		ObjectAnimator foldAnim = ObjectAnimator.ofFloat(mFoldLayout,
				"foldFactor", 0, 1);
		foldAnim.setDuration(duration);
		foldAnim.setInterpolator(interpolator);
		foldAnim.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.INVISIBLE);
				mFoldLayout.removeAllViews();
				parentView.removeView(mFoldLayout);
				parentView.addView(view, positionView);
				if (getListener() != null) {
					getListener().onAnimationEnd(FoldAnimation.this);
				}
			}
		});
		foldAnim.start();
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
	public FoldAnimation setNumOfFolds(int numOfFolds) {
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
	public FoldAnimation setOrientation(Orientation orientation) {
		this.orientation = orientation;
		return this;
	}

	/**
	 * The available anchor factors range from 0 to 1. For example, in order to
	 * anchor the fold at the top, the anchor factor should be 0.
	 * 
	 * @return The anchor factor of the fold.
	 */
	public float getAnchorFactor() {
		return anchorFactor;
	}

	/**
	 * The available anchor factors range from 0 to 1. For example, in order to
	 * anchor the fold at the top, the anchor factor should be 0.
	 * 
	 * @param anchorFactor
	 *            The anchor factor of the fold to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public FoldAnimation setAnchorFactor(float anchorFactor) {
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
	public FoldAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public FoldAnimation setDuration(long duration) {
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
	public FoldAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
