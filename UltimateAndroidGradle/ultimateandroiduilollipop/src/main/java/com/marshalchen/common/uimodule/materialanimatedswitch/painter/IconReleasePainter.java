package com.marshalchen.common.uimodule.materialanimatedswitch.painter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import com.marshalchen.common.uimodule.materialanimatedswitch.MaterialAnimatedSwitchState;
import com.marshalchen.common.uimodule.materialanimatedswitch.observer.BallFinishObservable;
import com.marshalchen.ultimateandroiduilollipop.R;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Adrián García Lomas
 */
public class IconReleasePainter extends IconPainter {

  private ValueAnimator enterXAnimator;
  private ValueAnimator enterYAnimator;
  private ValueAnimator exitXAnimator;
  private ValueAnimator exitYAnimator;
  private ValueAnimator exitAlphaAnimator;
  private int enterYAnimationStart;
  private int exitYAnimatorFinish;
  private int exitXAnimationStart;
  private BallFinishObservable ballFinishObservable;
  private MaterialAnimatedSwitchState actualState;
  private int iconMargin;
  private int middle;
  private boolean alphaEnterTrigger = false;

  public IconReleasePainter(Context context, Bitmap bitmap,
      BallFinishObservable ballFinishObservable, int margin) {
    super(context, bitmap, margin);
    initValueAnimator();
    this.ballFinishObservable = ballFinishObservable;
    initObserver();
  }

  @Override protected void initBitmap() {
    super.initBitmap();
  }

  private void initValueAnimator() {
    int movementAnimationDuration = context.getResources().getInteger(R.integer.mas_animation_duration);
    int alphaAnimationDuration =
        context.getResources().getInteger(R.integer.mas_alpha_animation_duration);
    int curveCompensation =
        context.getResources().getInteger(R.integer.mas_animation_curvature_compensation);

    enterXAnimator = ValueAnimator.ofInt(0, width);
    enterXAnimator.setDuration(movementAnimationDuration);
    enterXAnimator.addUpdateListener(new EnterXAnimationListener());

    exitXAnimator = ValueAnimator.ofInt();
    exitXAnimator.setDuration(movementAnimationDuration);
    exitXAnimator.addUpdateListener(new EnterXAnimationListener());

    enterYAnimator = ValueAnimator.ofInt(width, 0);
    enterYAnimator.setDuration(movementAnimationDuration - curveCompensation);
    enterYAnimator.addUpdateListener(new EnterYAnimationListener());

    exitYAnimator = ValueAnimator.ofInt();
    exitYAnimator.setDuration(movementAnimationDuration);
    exitYAnimator.addUpdateListener(new EnterYAnimationListener());

    exitAlphaAnimator = ValueAnimator.ofInt(0, 255);
    exitAlphaAnimator.setDuration(alphaAnimationDuration);
    exitAlphaAnimator.addUpdateListener(new AlphaAnimatorUpdateListener());
    exitAlphaAnimator.addListener(new AlphaAnimatorStateListener());
  }

  @Override public void setColor(int color) {
    //Empty
  }

  @Override public int getColor() {
    return 0;
  }

  @Override public void setState(MaterialAnimatedSwitchState state) {
    this.actualState = state;
    switch (state) {
      case INIT:
        isVisible = true;
        break;
      case PRESS:
        exitYAnimator.start();
        exitXAnimator.reverse();
        exitAlphaAnimator.reverse();
        break;
      case RELEASE:
        isVisible = true;
        enterXAnimator.reverse();
        enterYAnimator.start();
        break;
    }
  }

  private void initObserver() {
    ballFinishObservable.addObserver(new BallFinishListener());
  }

  private class BallFinishListener implements Observer {

    @Override public void update(Observable observable, Object data) {
      BallFinishObservable.BallState ballState = ((BallFinishObservable) observable).getState();
      switch (ballState) {
        case PRESS:
          isVisible = false;
          break;
      }
    }
  }

  @Override public void onSizeChanged(int height, int width) {
    super.onSizeChanged(height, width);
    initValues();
    initAnimationsValues();
    iconYPosition = middle - (iconMargin);
    iconXPosition = margin - iconMargin;
    enterXAnimator.setIntValues(0, width);
    enterYAnimator.setIntValues(enterYAnimationStart, middle);
    exitYAnimator.setIntValues(middle, exitYAnimatorFinish);
    exitXAnimator.setIntValues(exitXAnimationStart, 0);
  }

  private void initValues() {
    iconMargin = imageWidth / 2;
    middle = height / 2;
  }

  private void initAnimationsValues() {
    exitXAnimationStart = (int) context.getResources().getDimension(R.dimen.mas_exitXAnimationStart);
    exitYAnimatorFinish = (int) context.getResources().getDimension(R.dimen.mas_exitYAnimatorFinish);
    enterYAnimationStart = (int) context.getResources().getDimension(R.dimen.mas_enterYAnimationStart);
  }

  /**
   * Update the icon position in the x axis
   */
  private class EnterXAnimationListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      iconXPosition = ((int) animation.getAnimatedValue()) - iconMargin + margin;
    }
  }

  /**
   * Move the icon in the y axis and perform a trigger
   */
  private class EnterYAnimationListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      iconYPosition = ((int) animation.getAnimatedValue() - iconMargin);

      if (animation.getCurrentPlayTime() > animation.getDuration() / 2
          && !alphaEnterTrigger
          && actualState.equals(MaterialAnimatedSwitchState.RELEASE)) {
        exitAlphaAnimator.start();
        alphaEnterTrigger = true;
      }
    }
  }

  /**
   * Update the alpha
   */
  private class AlphaAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      paint.setAlpha((Integer) animation.getAnimatedValue());
    }
  }

  /**
   * Set alpha trigger to false on animation end
   */
  private class AlphaAnimatorStateListener implements ValueAnimator.AnimatorListener {

    @Override public void onAnimationStart(Animator animation) {
      //Empty
    }

    @Override public void onAnimationEnd(Animator animation) {
      alphaEnterTrigger = false;
    }

    @Override public void onAnimationCancel(Animator animation) {
      //Empty
    }

    @Override public void onAnimationRepeat(Animator animation) {
      //Empty
    }
  }
}
