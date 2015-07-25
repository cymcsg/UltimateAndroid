package com.marshalchen.common.uimodule.materialanimatedswitch.painter;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.marshalchen.common.uimodule.materialanimatedswitch.MaterialAnimatedSwitchState;
import com.marshalchen.common.uimodule.materialanimatedswitch.observer.BallMoveObservable;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Adrián García Lomas
 */
public class BasePainter implements SwitchInboxPinnedPainter, Observer {

  private Paint paint;
  private int bgColor;
  private int toBgColor;
  private int padding;
  private int height;
  private int width;
  private Paint toBgPainter;
  private ValueAnimator colorAnimator;
  private BallMoveObservable ballMoveObservable;

  public BasePainter(int bgColor, int toBgColor, int padding,
      BallMoveObservable ballMoveObservable) {
    this.bgColor = bgColor;
    this.toBgColor = toBgColor;
    this.padding = padding;
    this.ballMoveObservable = ballMoveObservable;
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setColor(bgColor);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setAntiAlias(true);

    toBgPainter = new Paint();
    toBgPainter.setColor(toBgColor);
    toBgPainter.setStrokeCap(Paint.Cap.ROUND);
    toBgPainter.setAntiAlias(true);
    toBgPainter.setAlpha(0);

    initColorAnimator();
    ballMoveObservable.addObserver(this);
  }

  private void initColorAnimator() {
    colorAnimator = ValueAnimator.ofInt(0, 255);
    colorAnimator.setDuration(100);
    colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        toBgPainter.setAlpha((Integer) animation.getAnimatedValue());
      }
    });
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawLine(padding, height / 2, width - padding, height / 2, paint);
    canvas.drawLine(padding, height / 2, width - padding, height / 2, toBgPainter);
  }

  @Override public int getColor() {
    return bgColor;
  }

  @Override public void setColor(int color) {

  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;
    paint.setStrokeWidth(height / 2);
    toBgPainter.setStrokeWidth(height / 2);
  }

  @Override public void setState(MaterialAnimatedSwitchState state) {
    //Empty
  }

  @Override public void update(Observable observable, Object data) {
    int value = ((BallMoveObservable) observable).getBallAnimationValue();
    colorAnimator.setCurrentPlayTime(value);
  }
}
