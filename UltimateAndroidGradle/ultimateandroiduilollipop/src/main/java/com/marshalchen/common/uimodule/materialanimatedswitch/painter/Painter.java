package com.marshalchen.common.uimodule.materialanimatedswitch.painter;

/**
 * Painter delegate the onDraw method in canvas to draw method here, each painter paints something
 * of the view
 *
 * @author Adrián García Lomas
 */

import android.graphics.Canvas;

public interface Painter<T extends Enum> {

  void draw(Canvas canvas);

  void setColor(int color);

  int getColor();

  void onSizeChanged(int height, int width);

  void setState(T state);
}
