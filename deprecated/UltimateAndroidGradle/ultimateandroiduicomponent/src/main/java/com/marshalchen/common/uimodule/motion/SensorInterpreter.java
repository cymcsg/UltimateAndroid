package com.marshalchen.common.uimodule.motion;

import android.content.Context;
import android.hardware.SensorEvent;
import android.view.Surface;
import android.view.WindowManager;

/*
 * Copyright 2014 Nathan VanBenschoten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class SensorInterpreter {

    private static final String TAG = SensorInterpreter.class.getName();

    /**
     * The standardized vectors corresponding to yaw, pitch, and roll.
     */
    private float[] mVectors;

    /**
     * The sensitivity the parallax effect has towards tilting.
     */
    private float mTiltSensitivity = 2.0f;

    /**
     * The forward tilt offset adjustment to counteract a natural forward phone tilt.
     */
    private float mForwardTiltOffset = 0.3f;

    public SensorInterpreter() {
        mVectors = new float[3];
    }

    /**
     * Converts sensor data to yaw, pitch, and roll
     *
     * @param context the context of the
     * @param event the event to interpret
     * @return and interpreted array of yaw, pitch, and roll vectors
     */
    public final float[] interpretSensorEvent(Context context, SensorEvent event) {
        if (event == null ||
                event.values.length < 3 ||
                event.values[0] == 0 ||
                event.values[1] == 0 ||
                event.values[2] == 0)
            return null;

        // Acquire rotation of screen
        final int rotation = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay()
                .getRotation();

        // Adjust for forward tilt based on screen orientation
        switch (rotation) {
            case Surface.ROTATION_90:
                mVectors[0] = event.values[0];
                mVectors[1] = event.values[2];
                mVectors[2] = -event.values[1];
                break;

            case Surface.ROTATION_180:
                mVectors[0] = event.values[0];
                mVectors[1] = event.values[1];
                mVectors[2] = event.values[2];
                break;

            case Surface.ROTATION_270:
                mVectors[0] = event.values[0];
                mVectors[1] = -event.values[2];
                mVectors[2] = event.values[1];
                break;

            default:
                mVectors[0] = event.values[0];
                mVectors[1] = -event.values[1];
                mVectors[2] = -event.values[2];
                break;
        }

        // Adjust roll for sensitivity differences based on pitch
        // double tiltScale = 1/Math.cos(mVectors[1] * Math.PI/180);
        // if (tiltScale > 12) tiltScale = 12;
        // if (tiltScale < -12) tiltScale = -12;
        // mVectors[2] *= tiltScale;

        // Make roll and pitch percentages out of 1
        mVectors[1] /= 90;
        mVectors[2] /= 90;

        // Add in forward tilt offset
        mVectors[1] -= mForwardTiltOffset;
        if (mVectors[1] < -1) mVectors[1] += 2;

        // Adjust for tilt sensitivity
        mVectors[1] *= mTiltSensitivity;
        mVectors[2] *= mTiltSensitivity;

        // Clamp values to image bounds
        if (mVectors[1] > 1) mVectors[1] = 1f;
        if (mVectors[1] < -1) mVectors[1] = -1f;

        if (mVectors[2] > 1) mVectors[2] = 1f;
        if (mVectors[2] < -1) mVectors[2] = -1f;

        return mVectors;
    }

    public float getForwardTiltOffset() {
        return mForwardTiltOffset;
    }

    public void setForwardTiltOffset(float forwardTiltOffset) {
        mForwardTiltOffset = forwardTiltOffset;
    }

    public float getTiltSensitivity() {
        return mTiltSensitivity;
    }

    public void setTiltSensitivity(float tiltSensitivity) {
        mTiltSensitivity = tiltSensitivity;
    }

}
