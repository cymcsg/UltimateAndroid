package com.marshalchen.common.commonUtils.uiUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

/**
 * Some method help do some UI works
 */
public class BasicUiUtils {
    /**
     * Hide soft keyboard method.
     * @param context
     * @param activity
     */
    public static void hiddenKeyboard(Context context, Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (activity.getCurrentFocus() != null) {
                if (activity.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(activity
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Hide soft keyboard by click。
     * @param context
     * @param activity
     * @param motionEvent Judge by motion event
     */
    public static void hiddenKeyBoardByClick(Context context, Activity activity, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            BasicUiUtils.hiddenKeyboard(context, activity);
        }
    }

    /**
     * Converte dp to pixels
     * @param context
     * @param dpValue
     * @return pixels
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) Math.ceil(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics()));
    }

    /**
     * Converting pixels to dp
     * @param context
     * @param pxValue
     * @return dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Converte dp to pixels
     * @param context
     * @param dpValue
     * @return pixels
     */
    public static float dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(dpValue * scale);
    }

    /**
     * Expand a view which has already collapsed
     * @param v
     */
    public static void expandViews(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    /**
     * Collapse a view which has already expanded
     * @param v
     */
    public static void collapseViews(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 1);
        v.startAnimation(a);
    }

    /**
     * Pop a simple alertdialog which only shows title,message and "OK" button
     *
     * @param context
     * @param title
     * @param message
     */
    public static void popAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setTitle(title)
                .setMessage(message).show();
    }

    /**
     * Pop a simple alertdialog which only shows title,message and "OK" button
     *
     * @param context
     * @param title
     * @param message
     */
    public static void popAlertDialog(Context context, int title, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setTitle(title)
                .setMessage(message).show();
    }

}
