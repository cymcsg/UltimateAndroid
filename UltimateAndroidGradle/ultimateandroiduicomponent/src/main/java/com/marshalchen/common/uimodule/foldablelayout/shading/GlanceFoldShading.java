package com.marshalchen.common.uimodule.foldablelayout.shading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;

public class GlanceFoldShading implements FoldShading {

    private static final int SHADOW_COLOR = Color.BLACK;
    private static final int SHADOW_MAX_ALPHA = 192;

    private final Paint mSolidShadow;

    private final Paint mGlancePaint;
    private final Bitmap mGlance;
    private final Rect mGlanceFrom;
    private final Rect mGlanceTo;

    public GlanceFoldShading(Context context, Bitmap glance) {
        mSolidShadow = new Paint();
        mSolidShadow.setColor(SHADOW_COLOR);

        mGlance = glance;
        mGlancePaint = new Paint();
        mGlancePaint.setDither(true);
        mGlancePaint.setFilterBitmap(true);
        mGlanceFrom = new Rect();
        mGlanceTo = new Rect();
    }

    @Override
    public void onPreDraw(Canvas canvas, Rect bounds, float rotation, int gravity) {
        // NO-OP
    }

    @Override
    public void onPostDraw(Canvas canvas, Rect bounds, float rotation, int gravity) {
        float intencity = getShadowIntencity(rotation, gravity);

        if (intencity > 0) {
            int alpha = (int) (SHADOW_MAX_ALPHA * intencity);
            mSolidShadow.setAlpha(alpha);
            canvas.drawRect(bounds, mSolidShadow);
        }

        boolean isDrawGlance = computeGlance(bounds, rotation, gravity);
        if (isDrawGlance) {
            canvas.drawBitmap(mGlance, mGlanceFrom, mGlanceTo, mGlancePaint);
        }
    }

    private float getShadowIntencity(float rotation, int gravity) {
        float intencity = 0;
        if (gravity == Gravity.TOP) {
            if (rotation > -90 && rotation < 0) { // (-90; 0) - rotation is applied
                intencity = -rotation / 90f;
            }
        }
        return intencity;
    }

    private boolean computeGlance(Rect bounds, float rotation, int gravity) {
        if (gravity == Gravity.BOTTOM) {
            if (rotation > 0 && rotation < 90) { // (0; 90) - rotation is applied
                final float aspect = (float) mGlance.getWidth() / (float) bounds.width();

                // computing glance offset
                final int distance = (int) (bounds.height() * ((rotation - 60f) / 15f));
                final int distanceOnGlance = (int) (distance * aspect);

                // computing "to" bounds
                int scaledGlanceHeight = (int) (mGlance.getHeight() / aspect);
                mGlanceTo.set(bounds.left, bounds.top + distance, bounds.right, bounds.top + distance + scaledGlanceHeight);
                if (!mGlanceTo.intersect(bounds)) {
                    // glance is not visible
                    return false;
                }

                // computing "from" bounds
                int scaledBoundsHeight = (int) (bounds.height() * aspect);
                mGlanceFrom.set(0, -distanceOnGlance, mGlance.getWidth(), -distanceOnGlance + scaledBoundsHeight);
                if (!mGlanceFrom.intersect(0, 0, mGlance.getWidth(), mGlance.getHeight())) {
                    // glance is not visible, should not happen due to previouse check
                    return false;
                }

                return true;
            }
        }

        return false;
    }

}
