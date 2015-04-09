package com.marshalchen.common.uimodule.smoothswitch;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 
 * @author Dean.Ding
 * 
 */
public class SwitchAnimationUtil {
	private int mOrderIndex = 0;
	private int mDelay = 100;
	private int mDuration = 300;

	public SwitchAnimationUtil() {

	}

	public void startAnimation(View root, AnimationType type) {

		ViewUtils.init(root.getContext());
		bindAnimation(root, 0, type);
	}

	private void bindAnimation(View view, int depth, AnimationType type) {

		if (view instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) view;
			if (type == AnimationType.HORIZON_CROSS) {
				/*
				 * Something wrong with it... Fixing
				 */

				for (int i = 0; i < group.getChildCount(); i++) {
					bindAnimation(group.getChildAt(i), depth + 1,
							i % 2 == 0 ? AnimationType.HORIZION_LEFT
									: AnimationType.HORIZION_RIGHT);
				}
			} else {
				for (int i = 0; i < group.getChildCount(); i++) {
					bindAnimation(group.getChildAt(i), depth + 1, type);
				}
			}
		} else {
			runAnimation(view, mDelay * mOrderIndex, type);
			mOrderIndex++;
		}
	}

	private void runAnimation(View view, long delay, AnimationType type) {
		switch (type) {
		case ROTATE:
			runRotateAnimation(view, delay);
			break;
		case ALPHA:
			runAlphaAnimation(view, delay);
			break;
		case HORIZION_LEFT:
			runHorizonLeftAnimation(view, delay);
			break;
		case HORIZION_RIGHT:
			runHorizonRightAnimation(view, delay);
			break;
		case HORIZON_CROSS:
			// NOT SUPPORT NOW
			// May be something for List
			break;
		case SCALE:
			runScaleAnimation(view, delay);
			break;
		case FLIP_HORIZON:
			runFlipHorizonAnimation(view, delay);
			break;
		case FLIP_VERTICAL:
			runFlipVertialAnimation(view, delay);
			break;
		default:
			break;
		}
	}

	private void runHorizonLeftAnimation(View view, long delay) {
		view.setAlpha(0);
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
				"translationX", -ViewUtils.getScreenWidth(), 0);
		objectAnimator.setInterpolator(new LinearInterpolator());
		ObjectAnimator objectAnimatorAlpha = ObjectAnimator.ofFloat(view,
				"alpha", 0f, 1f);
		AnimatorSet set = new AnimatorSet();
		set.setDuration(mDuration);
		set.setStartDelay(delay);
		set.playTogether(objectAnimator, objectAnimatorAlpha);
		set.start();
	}

	private void runHorizonRightAnimation(View view, long delay) {
		view.setAlpha(0);
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
				"translationX", ViewUtils.getScreenWidth(), 0);
		objectAnimator.setInterpolator(new LinearInterpolator());
		ObjectAnimator objectAnimatorAlpha = ObjectAnimator.ofFloat(view,
				"alpha", 0f, 1f);
		AnimatorSet set = new AnimatorSet();
		set.setStartDelay(delay);
		set.setDuration(mDuration);
		set.playTogether(objectAnimator, objectAnimatorAlpha);
		set.start();
	}

	private void runAlphaAnimation(View view, long delay) {
		view.setAlpha(0);
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha",
				0, 1);
		objectAnimator.setStartDelay(delay);
		objectAnimator.setDuration(mDuration);
		objectAnimator.setInterpolator(new LinearInterpolator());
		objectAnimator.start();
	}

	private void runRotateAnimation(View view, long delay) {
		view.setAlpha(0);
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
				"rotation", 0f, 360f);
		ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleX",
				0f, 1f);
		ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view, "scaleY",
				0f, 1f);
		ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(view, "alpha",
				0f, 1f);

		objectAnimator2.setInterpolator(new AccelerateInterpolator(1.0f));
		objectAnimator3.setInterpolator(new AccelerateInterpolator(1.0f));

		set.setDuration(mDuration);
		set.playTogether(objectAnimator, objectAnimator2, objectAnimator3,
				objectAnimator4);
		set.setStartDelay(delay);
		set.start();
	}

	private void runScaleAnimation(View view, long delay) {
		view.setAlpha(0);
		AnimatorSet set = new AnimatorSet();

		ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleX",
				0f, 1f);
		ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view, "scaleY",
				0f, 1f);
		ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(view, "alpha",
				0f, 1f);
		set.setDuration(mDuration);
		set.playTogether(objectAnimator2, objectAnimator3, objectAnimator4);
		set.setStartDelay(delay);
		set.start();
	}

	private void runFlipVertialAnimation(View view, long delay) {
		view.setAlpha(0);
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view,
				"rotationX", -180f, 0f);
		ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "alpha",
				0f, 1f);
		set.setDuration(mDuration);
		set.playTogether(objectAnimator1, objectAnimator2);
		set.setStartDelay(delay);
		set.start();
	}

	private void runFlipHorizonAnimation(View view, long delay) {
		view.setAlpha(0);
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view,
				"rotationY", -180f, 0f);
		ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "alpha",
				0f, 1f);
		set.setDuration(mDuration);
		set.playTogether(objectAnimator1, objectAnimator2);
		set.setStartDelay(delay);
		set.start();
	}

	public enum AnimationType {
		ALPHA, ROTATE, HORIZION_LEFT, HORIZION_RIGHT, HORIZON_CROSS, SCALE, FLIP_HORIZON, FLIP_VERTICAL
	}
}
