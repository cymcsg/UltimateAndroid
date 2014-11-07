package com.marshalchen.common.uimodule.easyandroidanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.marshalchen.common.uimodule.easyandroidanimations.Animation;
import com.marshalchen.common.uimodule.easyandroidanimations.AnimationListener;

/**
 * This animation creates a bitmap of the view, divides them into customizable
 * number of X and Y parts and translates the parts away from the center of the
 * view to mimic an explosion. The number of parts can vary from 1x2 to 3x3. The
 * view is set to invisible and added back for reuse.
 * 
 * @author SiYao
 * 
 */
public class ExplodeAnimation extends Animation {

	public final static int MATRIX_1X2 = 12;
	public final static int MATRIX_1X3 = 13;
	public final static int MATRIX_2X1 = 21;
	public final static int MATRIX_2X2 = 22;
	public final static int MATRIX_2X3 = 23;
	public final static int MATRIX_3X1 = 31;
	public final static int MATRIX_3X2 = 32;
	public final static int MATRIX_3X3 = 33;

	private int xParts, yParts;

	ViewGroup parentView;
	int matrix;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;

	/**
	 * This animation creates a bitmap of the view, divides them into
	 * customizable number of X and Y parts and translates the parts away from
	 * the center of the view to mimic an explosion. The number of parts can
	 * vary from 1x2 to 3x3. The view is set to invisible and added back for
	 * reuse.
	 * 
	 * @param view
	 *            The view to be animated.
	 */
	public ExplodeAnimation(View view) {
		this.view = view;
		setExplodeMatrix(MATRIX_3X3);
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		final LinearLayout explodeLayout = new LinearLayout(view.getContext());
		LinearLayout[] layouts = new LinearLayout[yParts];
		parentView = (ViewGroup) view.getParent();
		explodeLayout.setLayoutParams(view.getLayoutParams());
		explodeLayout.setOrientation(LinearLayout.VERTICAL);
		explodeLayout.setClipChildren(false);

		view.setDrawingCacheEnabled(true);
		Bitmap viewBmp = view.getDrawingCache(true);
		int totalParts = xParts * yParts, bmpWidth = viewBmp.getWidth()
				/ xParts, bmpHeight = viewBmp.getHeight() / yParts, widthCount = 0, heightCount = 0, middleXPart = (xParts - 1) / 2;
		int[] translation = new int[2];
		ImageView[] imageViews = new ImageView[totalParts];

		for (int i = 0; i < totalParts; i++) {
			int translateX = 0, translateY = 0;
			if (i % xParts == 0) {
				if (i != 0)
					heightCount++;
				widthCount = 0;
				layouts[heightCount] = new LinearLayout(view.getContext());
				layouts[heightCount].setClipChildren(false);
				translation = sideTranslation(heightCount, bmpWidth, bmpHeight,
						xParts, yParts);
				translateX = translation[0];
				translateY = translation[1];
			} else if (i % xParts == xParts - 1) {
				translation = sideTranslation(heightCount, -bmpWidth,
						bmpHeight, xParts, yParts);
				translateX = translation[0];
				translateY = translation[1];
			} else {
				if (widthCount == middleXPart) {
					if (heightCount == 0) {
						translateX = 0;
						if (yParts != 1) {
							translateY = -bmpHeight;
						}
					} else if (heightCount == yParts - 1) {
						translateX = 0;
						translateY = bmpHeight;
					}
				}
			}
			if (xParts == 1) {
				translation = sideTranslation(heightCount, 0, bmpHeight,
						xParts, yParts);
				translateX = translation[0];
				translateY = translation[1];
			}

			imageViews[i] = new ImageView(view.getContext());
			imageViews[i]
					.setImageBitmap(Bitmap.createBitmap(viewBmp, bmpWidth
							* widthCount, bmpHeight * heightCount, bmpWidth,
							bmpHeight));
			imageViews[i].animate().translationXBy(translateX)
					.translationYBy(translateY).alpha(0)
					.setInterpolator(interpolator).setDuration(duration);
			layouts[heightCount].addView(imageViews[i]);
			widthCount++;
		}

		for (int i = 0; i < yParts; i++)
			explodeLayout.addView(layouts[i]);
		final int positionView = parentView.indexOfChild(view);
		parentView.removeView(view);
		parentView.addView(explodeLayout, positionView);

		ViewGroup rootView = (ViewGroup) explodeLayout.getRootView();
		while (!parentView.equals(rootView)) {
			parentView.setClipChildren(false);
			parentView = (ViewGroup) parentView.getParent();
		}
		rootView.setClipChildren(false);

		imageViews[0].animate().setListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				parentView = (ViewGroup) explodeLayout.getParent();
				view.setLayoutParams(explodeLayout.getLayoutParams());
				view.setVisibility(View.INVISIBLE);
				parentView.removeView(explodeLayout);
				parentView.addView(view, positionView);
				if (getListener() != null) {
					getListener().onAnimationEnd(ExplodeAnimation.this);
				}
			}
		});
	}

	private int[] sideTranslation(int heightCount, int bmpWidth, int bmpHeight,
			int xParts, int yParts) {
		int[] translation = new int[2];
		int middleYPart = (yParts - 1) / 2;
		if (heightCount == 0) {
			translation[0] = -bmpWidth;
			translation[1] = -bmpHeight;
		} else if (heightCount == yParts - 1) {
			translation[0] = -bmpWidth;
			translation[1] = bmpHeight;
		}

		if (yParts % 2 != 0) {
			if (heightCount == middleYPart) {
				translation[0] = -bmpWidth;
				translation[1] = 0;
			}
		}
		return translation;
	}

	/**
	 * The available matrices are <code>MATRIX_1X2</code>,
	 * <code>MATRIX_1X3</code>, <code>MATRIX_2X1</code>, <code>MATRIX_2X2</code>
	 * , <code>MATRIX_2X3</code>, <code>MATRIX_3X1</code>,
	 * <code>MATRIX_3X2</code> and <code>MATRIX_3X3</code>.
	 * 
	 * @return The matrix that determines the number of X and Y parts.
	 */
	public int getExplodeMatrix() {
		return matrix;
	}

	/**
	 * The available matrices are <code>MATRIX_1X2</code>,
	 * <code>MATRIX_1X3</code>, <code>MATRIX_2X1</code>, <code>MATRIX_2X2</code>
	 * , <code>MATRIX_2X3</code>, <code>MATRIX_3X1</code>,
	 * <code>MATRIX_3X2</code> and <code>MATRIX_3X3</code>.
	 * 
	 * @param matrix
	 *            The matrix that determines the number of X and Y parts to set.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public ExplodeAnimation setExplodeMatrix(int matrix) {
		this.matrix = matrix;
		xParts = matrix / 10;
		yParts = matrix % 10;
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
	public ExplodeAnimation setInterpolator(TimeInterpolator interpolator) {
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
	public ExplodeAnimation setDuration(long duration) {
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
	 * 
	 * @param listener
	 *            The listener to set for the end of the animation.
	 * @return This object, allowing calls to methods in this class to be
	 *         chained.
	 */
	public ExplodeAnimation setListener(AnimationListener listener) {
		this.listener = listener;
		return this;
	}

}
