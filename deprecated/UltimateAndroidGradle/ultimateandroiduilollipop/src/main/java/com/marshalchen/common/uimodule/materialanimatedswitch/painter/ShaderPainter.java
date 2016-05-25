package com.marshalchen.common.uimodule.materialanimatedswitch.painter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.marshalchen.common.uimodule.materialanimatedswitch.MaterialAnimatedSwitchState;

/**
 * @author Adrián García Lomas
 */
public class ShaderPainter implements SwitchInboxPinnedPainter {

  private Paint maskPaint;
  private int height;
  private int width;
  private Bitmap mask;

  public ShaderPainter() {
    maskPaint = new Paint();
    maskPaint.setColor(Color.TRANSPARENT);
    maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    maskPaint.setStyle(Paint.Style.FILL);
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawLine(0, height / 2, width, height / 2, maskPaint);
  }

  @Override public void setColor(int color) {
    //Empty
  }

  @Override public int getColor() {
    return 0;
  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;
    createMask(height, width, 10);
  }

  @Override public void setState(MaterialAnimatedSwitchState state) {
    //Empty
  }

  private void createMask(int w, int h, int radius) {
    if (mask != null) {
      mask.recycle();
    }

    mask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas maskCanvas = new Canvas(mask);
    maskCanvas.drawCircle(w, h, radius, maskPaint);
  }
}
