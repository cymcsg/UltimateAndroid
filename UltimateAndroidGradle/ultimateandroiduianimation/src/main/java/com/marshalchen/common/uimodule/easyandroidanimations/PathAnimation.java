package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * This animation translates the view within its parent view and according to
 * the ArrayList<Point> provided by the user. The values of X and Y in each
 * Point must be in the range of 0-100. Note: The status bar and action bar are
 * not taken into consideration for the translation.
 * 
 * @author SiYao
 * 
 */
public class PathAnimation extends Animation implements Combinable {

	public static final int ANCHOR_CENTER = 0, ANCHOR_TOP_LEFT = 1,
			ANCHOR_TOP_RIGHT = 2, ANCHOR_BOTTOM_LEFT = 3,
			ANCHOR_BOTTOM_RIGHT = 4;

	ArrayList<Point> points;
	int anchorPoint;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation translates the view within its parent view and according
	 * to the ArrayList<Point> provided by the user. The values of X and Y in
	 * each Point must be in the range of 0-100. Note: The status bar and action
	 * bar are not taken into consideration for the translation.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public PathAnimation(View view) {
		this.view = view;
		points = null;
		anchorPoint = ANCHOR_CENTER;
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

		AnimatorSet pathSet = new AnimatorSet();
		int numOfPoints = points.size();
		AnimatorSet[] pathAnimSetArray = new AnimatorSet[numOfPoints];
		List<Animator> pathAnimList = new ArrayList<Animator>();

		parentView = (ViewGroup) view.getParent();
		int parentWidth = parentView.getWidth(), parentHeight = parentView
				.getHeight(), viewWidth = view.getWidth(), viewHeight = view
				.getHeight();
		float posX, posY;
		for (int i = 0; i < numOfPoints; i++) {
			posX = (points.get(i).x / 100f * parentWidth);
			posY = (points.get(i).y / 100f * parentHeight);

			switch (anchorPoint) {
			case ANCHOR_CENTER:
				posX = posX - viewWidth / 2;
				posY = posY - viewHeight / 2;
				break;
			case ANCHOR_TOP_RIGHT:
				posX -= viewWidth;
				break;
			case ANCHOR_BOTTOM_LEFT:
				posY -= viewHeight;
				break;
			case ANCHOR_BOTTOM_RIGHT:
				posX = posX - viewWidth;
				posY = posY - viewHeight;
				break;
			default:
				break;
			}
			pathAnimSetArray[i] = new AnimatorSet();
			pathAnimSetArray[i].playTogether(
					ObjectAnimator.ofFloat(view, View.X, posX),
					ObjectAnimator.ofFloat(view, View.Y, posY));
			pathAnimList.add(pathAnimSetArray[i]);
		}

		pathSet.playSequentially(pathAnimList);
		pathSet.setInterpolator(interpolator);
		pathSet.setDuration(duration / numOfPoints);
		pathSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				if (getListener() != null) {
					getListener().onAnimationEnd(PathAnimation.this);
				}
			}
		});
		return pathSet;
	}

	/**
	 * @return The ArrayList<Point> for the view to translate to.
	 * @see java.util.ArrayList
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            The ArrayList<Point> to set for the view to translate to.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 * @see java.util.ArrayList
	 */
	public PathAnimation setPoints(ArrayList<Point> points) {
		this.points = points;
		return this;
	}

	/**
	 * The available anchor points at which to translate the view are
	 * <code>ANCHOR_CENTER</code>, <code>ANCHOR_TOP_LEFT</code>,
	 * <code>ANCHOR_TOP_RIGHT</code>, <code>ANCHOR_BOTTOM_LEFT</code> and
	 * <code>ANCHOR_BOTTOM_RIGHT</code>.
	 * 
	 * @return The anchor point at which to translate the view.
	 */
	public int getAnchorPoint() {
		return anchorPoint;
	}

	/**
	 * The available anchor points at which to translate the view are
	 * <code>ANCHOR_CENTER</code>, <code>ANCHOR_TOP_LEFT</code>,
	 * <code>ANCHOR_TOP_RIGHT</code>, <code>ANCHOR_BOTTOM_LEFT</code> and
	 * <code>ANCHOR_BOTTOM_RIGHT</code>.
	 * 
	 * @param anchorPoint
	 *            The anchor point to set at which to translate the view.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public PathAnimation setAnchorPoint(int anchorPoint) {
		this.anchorPoint = anchorPoint;
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
	public PathAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public PathAnimation setDuration(long duration) {
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
	public PathAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}
}
