package com.marshalchen.common.uimodule.materialanimatedswitch;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author Adrián García Lomas
 */
public class Utils {

  /**
   * Convert Dp to Pixel
   */
  public static int dpToPx(float dp, Resources resources) {
    float px =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    return (int) px;
  }
}
