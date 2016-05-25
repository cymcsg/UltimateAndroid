package com.marshalchen.common.uimodule.materialanimatedswitch.painter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.marshalchen.common.uimodule.materialanimatedswitch.observer.BallFinishObservable;
import com.marshalchen.common.uimodule.materialanimatedswitch.observer.BallMoveObservable;
import com.marshalchen.common.uimodule.materialanimatedswitch.MaterialAnimatedSwitchState;
import com.marshalchen.ultimateandroiduilollipop.R;

/**
 * @author Adrián García Lomas
 */
public class BallPainter implements SwitchInboxPinnedPainter {

  protected Paint paint;
  protected Paint toBgPainter;
  protected int padding;
  protected int height;
  protected int width;
  protected int radius;
  protected int ballPositionX;
  protected int ballStartPositionX;
  protected int ballMovementRange;
  private ValueAnimator moveAnimator;
  private ValueAnimator colorAnimator;
  private MaterialAnimatedSwitchState actualState;
  private BallFinishObservable ballFinishObservable;
  private BallMoveObservable ballMoveObservable;
  private Context context;
  private int middle;
  private int bgColor;
  private int toBgColor;

  public BallPainter(int bgColor, int toBgColor, int padding,
      BallFinishObservable ballFinishObservable, BallMoveObservable ballMoveObservable,
      Context context) {
    this.bgColor = bgColor;
    this.toBgColor = toBgColor;
    this.padding = padding;
    this.ballFinishObservable = ballFinishObservable;
    this.ballMoveObservable = ballMoveObservable;
    this.context = context;
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setColor(bgColor);
    paint.setStyle(Paint.Style.FILL);
    paint.setAntiAlias(true);
    toBgPainter = new Paint();
    toBgPainter.setColor(toBgColor);
    toBgPainter.setStyle(Paint.Style.FILL);
    toBgPainter.setAntiAlias(true);
    toBgPainter.setAlpha(0);
    radius = (int) context.getResources().getDimension(R.dimen.mas_ball_radius);
    ballStartPositionX = (int) context.getResources().getDimension(R.dimen.mas_ball_start_position);
    ballPositionX = padding;
  }

  private void initAnimator() {
    int from = padding;
    int to = width - padding;
    ballMovementRange = to - from;
    moveAnimator = ValueAnimator.ofInt(from, to);
    moveAnimator.addUpdateListener(new BallAnimatorListener());
    moveAnimator.addListener(new BallAnimatorFinishListener());
  }

  private void initColorAnimator() {
    colorAnimator = ValueAnimator.ofInt(0, 255);
    colorAnimator.setDuration(ballMovementRange);
    colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        toBgPainter.setAlpha((Integer) animation.getAnimatedValue());
      }
    });
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawCircle(ballPositionX, middle, radius, paint);
    canvas.drawCircle(ballPositionX, middle, radius, toBgPainter);
  }

  @Override public void setColor(int color) {
    this.bgColor = color;
  }

  @Override public int getColor() {
    return bgColor;
  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;
    middle = height / 2;
    initAnimator();
    initColorAnimator();
  }

  @Override public void setState(MaterialAnimatedSwitchState state) {
    switch (state) {
      case PRESS:
        actualState = MaterialAnimatedSwitchState.PRESS;
        moveAnimator.start();
        break;
      case RELEASE:
        actualState = MaterialAnimatedSwitchState.RELEASE;
        moveAnimator.reverse();
    }
  }

  private class BallAnimatorFinishListener implements ValueAnimator.AnimatorListener {

    @Override public void onAnimationStart(Animator animation) {
      ballFinishObservable.setBallState(BallFinishObservable.BallState.MOVE);
    }

    @Override public void onAnimationEnd(Animator animation) {
      if (actualState.equals(MaterialAnimatedSwitchState.PRESS)) {
        ballFinishObservable.setBallState(BallFinishObservable.BallState.PRESS);
      } else {
        ballFinishObservable.setBallState(BallFinishObservable.BallState.RELEASE);
      }
    }

    @Override public void onAnimationCancel(Animator animation) {
      //Empty
    }

    @Override public void onAnimationRepeat(Animator animation) {
      //Empty
    }
  }

  private class BallAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      int value = (int) animation.getAnimatedValue();
      //Move the ball
      ballPositionX = value;
      //1- Get pixel of movement from 0 to movementRange
      int pixelMove = value - padding;
      //Transform the range movement to a 0 - 100 range
      int rangeValue = getAnimatedRange(pixelMove);
      //Change the color animation to the actual range value (duration is 100)
      colorAnimator.setCurrentPlayTime(rangeValue);
      //Set ball position to
      ballMoveObservable.setBallPosition(ballPositionX);
      //Put this value on a observable the listeners know the state of the movement in a range of 0
      //to 100
      ballMoveObservable.setBallAnimationValue(rangeValue);
    }

    private int getAnimatedRange(int value) {
      return ((value * 100) / ballMovementRange);
    }
  }

}
