package com.marshalchen.common.uimodule.materialanimatedswitch.painter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import com.marshalchen.common.uimodule.materialanimatedswitch.MaterialAnimatedSwitchState;
import com.marshalchen.common.uimodule.materialanimatedswitch.observer.BallFinishObservable;
import com.marshalchen.common.uimodule.materialanimatedswitch.observer.BallMoveObservable;
import com.marshalchen.ultimateandroiduilollipop.R;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Adrián García Lomas
 */
public class IconPressPainter extends IconPainter {

  private ValueAnimator enterAnimator;
  private ValueAnimator exitAnimator;
  private BallFinishObservable ballFinishObservable;
  private int enterAnimationStartValue;
  private int exitAnimationExitValue;
  private int middle;
  private int iconMargin;
  private int ballRadius;
  private BallMoveObservable ballMoveObservable;

  public IconPressPainter(Context context, Bitmap bitmap, BallFinishObservable ballFinishObservable,
      BallMoveObservable ballMoveObservable, int margin) {
    super(context, bitmap, margin);
    initValueAnimator();
    this.ballFinishObservable = ballFinishObservable;
    this.ballMoveObservable = ballMoveObservable;
    initObserver();
  }

  private void initValueAnimator() {
    int movementAnimationDuration = context.getResources().getInteger(R.integer.mas_animation_duration);
    int exitAnimationDuration = context.getResources().getInteger(R.integer.mas_exitAnimator);

    enterAnimator = ValueAnimator.ofInt();
    enterAnimator.setDuration(movementAnimationDuration);
    enterAnimator.addUpdateListener(new EnterValueAnimatorListener());

    exitAnimator = ValueAnimator.ofInt();
    exitAnimator.setDuration(exitAnimationDuration);
    exitAnimator.addUpdateListener(new ExitValueAnimatorListener());
  }

  @Override public void onSizeChanged(int height, int width) {
    super.onSizeChanged(height, width);
    initValues();
    int iconCenterY = middle - (iconMargin);
    initAnimationValues();
    enterAnimator.setIntValues(enterAnimationStartValue, iconCenterY);
    exitAnimator.setIntValues(iconCenterY, iconCenterY + exitAnimationExitValue);
  }

  private void initValues() {
    middle = height / 2;
    iconMargin = imageWidth / 2;
    ballRadius = (int) context.getResources().getDimension(R.dimen.mas_ball_radius);
    iconXPosition = (width - ballRadius) + iconMargin;
  }

  private void initAnimationValues() {
    enterAnimationStartValue =
        (int) context.getResources().getDimension(R.dimen.mas_enterAnimationStartValue);
    exitAnimationExitValue =
        (int) context.getResources().getDimension(R.dimen.mas_exitAnimationExitValue);
  }

  @Override public void setColor(int color) {
    //Empty
  }

  @Override public int getColor() {
    return 0;
  }

  private void initObserver() {
    ballFinishObservable.addObserver(new BallFinishListener());
    ballMoveObservable.addObserver(new BallMoveListener());
  }

  @Override public void setState(MaterialAnimatedSwitchState state) {
    switch (state) {
      case PRESS:
        isVisible = true;
        enterAnimator.start();
        break;
      case RELEASE:
        exitAnimator.start();
    }
  }

  private class BallFinishListener implements Observer {

    @Override public void update(Observable observable, Object data) {
      BallFinishObservable.BallState ballState = ((BallFinishObservable) observable).getState();
      switch (ballState) {
        case RELEASE:
          isVisible = false;
          break;
      }
    }
  }

  /**
   * Listener for move the icon with the ball movement
   */
  private class BallMoveListener implements Observer {

    @Override public void update(Observable observable, Object data) {
      BallMoveObservable ballMoveObservable = (BallMoveObservable) observable;
      int ballPositionX = ballMoveObservable.getBallPosition();
      iconXPosition = ballPositionX - iconMargin;
    }
  }

  private class EnterValueAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
    @Override public void onAnimationUpdate(ValueAnimator animation) {
      iconYPosition = (int) animation.getAnimatedValue();
    }
  }

  private class ExitValueAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      iconYPosition = (int) animation.getAnimatedValue();
    }
  }
}
