package com.marshalchen.common.uimodule.arcmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import com.marshalchen.common.uimodule.R;

public class RayLayout extends ViewGroup {

	/**
	 * children will be set the same size.
	 */
	private int mChildSize;

	/* the distance between child Views */
	private int mChildGap;

	/* left space to place the switch button */
	private int mLeftHolderWidth;

	private boolean mExpanded = false;

	public RayLayout(Context context) {
		super(context);
	}

	public RayLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ArcLayout, 0, 0);
			mChildSize = Math.max(a.getDimensionPixelSize(R.styleable.ArcLayout_childSize, 0), 0);
			a.recycle();

			a = getContext().obtainStyledAttributes(attrs, R.styleable.RayLayout, 0, 0);
			mLeftHolderWidth = Math.max(a.getDimensionPixelSize(R.styleable.RayLayout_leftHolderWidth, 0), 0);
			a.recycle();

		}
	}

	private static int computeChildGap(final float width, final int childCount, final int childSize, final int minGap) {
		return Math.max((int) (width / childCount - childSize), minGap);
	}

	private static Rect computeChildFrame(final boolean expanded, final int paddingLeft, final int childIndex,
			final int gap, final int size) {
		final int left = expanded ? (paddingLeft + childIndex * (gap + size) + gap) : ((paddingLeft - size) / 2);

		return new Rect(left, 0, left + size, size);
	}

	@Override
	protected int getSuggestedMinimumHeight() {
		return mChildSize;
	}

	@Override
	protected int getSuggestedMinimumWidth() {
		return mLeftHolderWidth + mChildSize * getChildCount();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(getSuggestedMinimumHeight(), MeasureSpec.EXACTLY));

		final int count = getChildCount();
		mChildGap = computeChildGap(getMeasuredWidth() - mLeftHolderWidth, count, mChildSize, 0);

		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY));
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int paddingLeft = mLeftHolderWidth;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			Rect frame = computeChildFrame(mExpanded, paddingLeft, i, mChildGap, mChildSize);
			getChildAt(i).layout(frame.left, frame.top, frame.right, frame.bottom);
		}

	}

	/**
	 * refers to {@link LayoutAnimationController#getDelayForView(android.view.View view)}
	 */
	private static long computeStartOffset(final int childCount, final boolean expanded, final int index,
			final float delayPercent, final long duration, Interpolator interpolator) {
		final float delay = delayPercent * duration;
		final long viewDelay = (long) (getTransformedIndex(expanded, childCount, index) * delay);
		final float totalDelay = delay * childCount;

		float normalizedDelay = viewDelay / totalDelay;
		normalizedDelay = interpolator.getInterpolation(normalizedDelay);

		return (long) (normalizedDelay * totalDelay);
	}

	private static int getTransformedIndex(final boolean expanded, final int count, final int index) {
		return count - 1 - index;
	}

	private static Animation createExpandAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
			long startOffset, long duration, Interpolator interpolator) {
		Animation animation = new RotateAndTranslateAnimation(0, toXDelta, 0, toYDelta, 0, 720);
		animation.setStartOffset(startOffset);
		animation.setDuration(duration);
		animation.setInterpolator(interpolator);
		animation.setFillAfter(true);

		return animation;
	}

	private static Animation createShrinkAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
			long startOffset, long duration, Interpolator interpolator) {
		AnimationSet animationSet = new AnimationSet(false);
		animationSet.setFillAfter(true);

		final long preDuration = duration / 2;
		Animation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setStartOffset(startOffset);
		rotateAnimation.setDuration(preDuration);
		rotateAnimation.setInterpolator(new LinearInterpolator());
		rotateAnimation.setFillAfter(true);

		animationSet.addAnimation(rotateAnimation);

		Animation translateAnimation = new RotateAndTranslateAnimation(0, toXDelta, 0, toYDelta, 360, 720);
		translateAnimation.setStartOffset(startOffset + preDuration);
		translateAnimation.setDuration(duration - preDuration);
		translateAnimation.setInterpolator(interpolator);
		translateAnimation.setFillAfter(true);

		animationSet.addAnimation(translateAnimation);

		return animationSet;
	}

	private void bindChildAnimation(final View child, final int index, final long duration) {
		final boolean expanded = mExpanded;
		final int childCount = getChildCount();
		Rect frame = computeChildFrame(!expanded, mLeftHolderWidth, index, mChildGap, mChildSize);

		final int toXDelta = frame.left - child.getLeft();
		final int toYDelta = frame.top - child.getTop();

		Interpolator interpolator = mExpanded ? new AccelerateInterpolator() : new OvershootInterpolator(1.5f);
		final long startOffset = computeStartOffset(childCount, mExpanded, index, 0.1f, duration, interpolator);

		Animation animation = mExpanded ? createShrinkAnimation(0, toXDelta, 0, toYDelta, startOffset, duration,
				interpolator) : createExpandAnimation(0, toXDelta, 0, toYDelta, startOffset, duration, interpolator);

		final boolean isLast = getTransformedIndex(expanded, childCount, index) == childCount - 1;
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (isLast) {
					postDelayed(new Runnable() {

						@Override
						public void run() {
							onAllAnimationsEnd();
						}
					}, 0);
				}
			}
		});

		child.setAnimation(animation);
	}

	public boolean isExpanded() {
		return mExpanded;
	}

	public void setChildSize(int size) {
		if (mChildSize == size || size < 0) {
			return;
		}

		mChildSize = size;

		requestLayout();
	}

	/**
	 * switch between expansion and shrinkage
	 * 
	 * @param showAnimation
	 */
	public void switchState(final boolean showAnimation) {
		if (showAnimation) {
			final int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				bindChildAnimation(getChildAt(i), i, 300);
			}
		}

		mExpanded = !mExpanded;

		if (!showAnimation) {
			requestLayout();
		}

		invalidate();
	}

	private void onAllAnimationsEnd() {
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			getChildAt(i).clearAnimation();
		}

		requestLayout();
	}

}
