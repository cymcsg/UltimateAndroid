/*
 * Copyright (C) 2012 Capricorn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.uimodule.arcmenu;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * An animation that controls the position of an object, and make the object
 * rotate on its center at the same time.
 * 
 */
public class RotateAndTranslateAnimation extends Animation {
    private int mFromXType = ABSOLUTE;

    private int mToXType = ABSOLUTE;

    private int mFromYType = ABSOLUTE;

    private int mToYType = ABSOLUTE;

    private float mFromXValue = 0.0f;

    private float mToXValue = 0.0f;

    private float mFromYValue = 0.0f;

    private float mToYValue = 0.0f;

    private float mFromXDelta;

    private float mToXDelta;

    private float mFromYDelta;

    private float mToYDelta;

    private float mFromDegrees;

    private float mToDegrees;

    private int mPivotXType = ABSOLUTE;

    private int mPivotYType = ABSOLUTE;

    private float mPivotXValue = 0.0f;

    private float mPivotYValue = 0.0f;

    private float mPivotX;

    private float mPivotY;

    /**
     * Constructor to use when building a TranslateAnimation from code
     * 
     * @param fromXDelta
     *            Change in X coordinate to apply at the start of the animation
     * @param toXDelta
     *            Change in X coordinate to apply at the end of the animation
     * @param fromYDelta
     *            Change in Y coordinate to apply at the start of the animation
     * @param toYDelta
     *            Change in Y coordinate to apply at the end of the animation
     * 
     * @param fromDegrees
     *            Rotation offset to apply at the start of the animation.
     * @param toDegrees
     *            Rotation offset to apply at the end of the animation.
     */
    public RotateAndTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
            float fromDegrees, float toDegrees) {
        mFromXValue = fromXDelta;
        mToXValue = toXDelta;
        mFromYValue = fromYDelta;
        mToYValue = toYDelta;

        mFromXType = ABSOLUTE;
        mToXType = ABSOLUTE;
        mFromYType = ABSOLUTE;
        mToYType = ABSOLUTE;

        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;

        mPivotXValue = 0.5f;
        mPivotXType = RELATIVE_TO_SELF;
        mPivotYValue = 0.5f;
        mPivotYType = RELATIVE_TO_SELF;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float degrees = mFromDegrees + ((mToDegrees - mFromDegrees) * interpolatedTime);
        if (mPivotX == 0.0f && mPivotY == 0.0f) {
            t.getMatrix().setRotate(degrees);
        } else {
            t.getMatrix().setRotate(degrees, mPivotX, mPivotY);
        }

        float dx = mFromXDelta;
        float dy = mFromYDelta;
        if (mFromXDelta != mToXDelta) {
            dx = mFromXDelta + ((mToXDelta - mFromXDelta) * interpolatedTime);
        }
        if (mFromYDelta != mToYDelta) {
            dy = mFromYDelta + ((mToYDelta - mFromYDelta) * interpolatedTime);
        }

        t.getMatrix().postTranslate(dx, dy);
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mFromXDelta = resolveSize(mFromXType, mFromXValue, width, parentWidth);
        mToXDelta = resolveSize(mToXType, mToXValue, width, parentWidth);
        mFromYDelta = resolveSize(mFromYType, mFromYValue, height, parentHeight);
        mToYDelta = resolveSize(mToYType, mToYValue, height, parentHeight);

        mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
        mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
    }
}