package com.marshalchen.common.uimodule.materialanimatedswitch.painter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.marshalchen.ultimateandroiduilollipop.R;

/**
 * @author Adrián García Lomas
 */
public abstract class IconPainter implements SwitchInboxPinnedPainter {

  protected Bitmap iconBitmap;
  protected Context context;
  protected Paint paint;
  protected int width;
  protected int height;
  protected int imageHeight;
  protected int imageWidth;
  protected boolean isVisible = false;
  protected int iconXPosition;
  protected int iconYPosition;
  protected int margin;

  public IconPainter(Context context, Bitmap bitmap, int margin) {
    this.context = context;
    this.iconBitmap = bitmap;
    this.margin = margin;
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setAntiAlias(true);
    initBitmap();
  }

  protected void initBitmap() {
    int iconSize = (int) context.getResources().getDimension(R.dimen.mas_icon_size);
    iconBitmap = Bitmap.createScaledBitmap(iconBitmap, iconSize, iconSize, false);
    imageHeight = iconBitmap.getHeight();
    imageWidth = iconBitmap.getWidth();
  }

  @Override public void draw(Canvas canvas) {
    if (isVisible) {
      canvas.drawBitmap(iconBitmap, iconXPosition, iconYPosition, paint);
    }
  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;
  }
}
