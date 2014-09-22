/*
 * Copyright (C) 2014 lafosca Studio, SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.uimodule.facecropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.media.FaceDetector;
import com.marshalchen.common.commonUtils.logUtils.Logs;

/**
 * An utility that crops faces from bitmaps.
 * It support multiple faces (max 8 by default) and crop them all, fitted in the same image.
 */
public class FaceCropper {

    private static final String LOG_TAG = FaceCropper.class.getSimpleName();

    public enum SizeMode {FaceMarginPx, EyeDistanceFactorMargin}

    ;

    private static final int MAX_FACES = 8;
    private static final int MIN_FACE_SIZE = 200;

    private int mFaceMinSize = MIN_FACE_SIZE;
    private int mFaceMarginPx = 100;
    private float mEyeDistanceFactorMargin = 2f;
    private int mMaxFaces = MAX_FACES;
    private SizeMode mSizeMode = SizeMode.EyeDistanceFactorMargin;

    public FaceCropper() {
    }

    public FaceCropper(int faceMarginPx) {
        setFaceMarginPx(faceMarginPx);
    }

    public FaceCropper(float eyesDistanceFactorMargin) {
        setEyeDistanceFactorMargin(eyesDistanceFactorMargin);
    }

    public int getMaxFaces() {
        return mMaxFaces;
    }

    public void setMaxFaces(int maxFaces) {
        this.mMaxFaces = maxFaces;
    }

    public int getFaceMinSize() {
        return mFaceMinSize;
    }

    public void setFaceMinSize(int faceMinSize) {
        mFaceMinSize = faceMinSize;
    }

    public int getFaceMarginPx() {
        return mFaceMarginPx;
    }

    public void setFaceMarginPx(int faceMarginPx) {
        mFaceMarginPx = faceMarginPx;
        mSizeMode = SizeMode.FaceMarginPx;
    }

    public SizeMode getSizeMode() {
        return mSizeMode;
    }

    public float getEyeDistanceFactorMargin() {
        return mEyeDistanceFactorMargin;
    }

    public void setEyeDistanceFactorMargin(float eyeDistanceFactorMargin) {
        mEyeDistanceFactorMargin = eyeDistanceFactorMargin;
        mSizeMode = SizeMode.EyeDistanceFactorMargin;
    }

    public Bitmap cropFace(Context ctx, int resDrawable) {
        // Set internal configuration to RGB_565
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        return cropFace(BitmapFactory.decodeResource(ctx.getResources(), resDrawable, bitmapOptions));
    }

    public Bitmap cropFace(Bitmap original) {
        Bitmap fixedBitmap = BitmapUtils.forceEvenBitmapSize(original);
        fixedBitmap = BitmapUtils.forceConfig565(fixedBitmap);

        FaceDetector faceDetector = new FaceDetector(
                fixedBitmap.getWidth(), fixedBitmap.getHeight(),
                mMaxFaces);

        FaceDetector.Face[] faces = new FaceDetector.Face[mMaxFaces];

        // The bitmap must be in 565 format (for now).
        int faceCount = faceDetector.findFaces(fixedBitmap, faces);

        Logs.d("face crop");

        if (faceCount == 0) {
            return fixedBitmap;
        }

        int initX = fixedBitmap.getWidth();
        int initY = fixedBitmap.getHeight();
        int endX = 0;
        int endY = 0;

        PointF centerFace = new PointF();

        // Calculates minimum box to fit all detected faces
        for (int i = 0; i < faceCount; i++) {
            FaceDetector.Face face = faces[i];

            // Eyes distance * 3 usually fits an entire face
            int faceSize = (int) (face.eyesDistance() * 3);
            if (SizeMode.FaceMarginPx.equals(mSizeMode)) {
                faceSize += mFaceMarginPx * 2; // *2 for top and down/right and left effect
            } else if (SizeMode.EyeDistanceFactorMargin.equals(mSizeMode)) {
                faceSize += face.eyesDistance() * mEyeDistanceFactorMargin;
            }

            faceSize = Math.max(faceSize, mFaceMinSize);

            face.getMidPoint(centerFace);

            int tInitX = (int) (centerFace.x - faceSize / 2);
            int tInitY = (int) (centerFace.y - faceSize / 2);
            tInitX = Math.max(0, tInitX);
            tInitY = Math.max(0, tInitY);

            int tEndX = tInitX + faceSize;
            int tEndY = tInitY + faceSize;
            tEndX = Math.min(tEndX, fixedBitmap.getWidth());
            tEndY = Math.min(tEndY, fixedBitmap.getHeight());

            initX = Math.min(initX, tInitX);
            initY = Math.min(initY, tInitY);
            endX = Math.max(endX, tEndX);
            endY = Math.max(endY, tEndY);
        }

        int sizeX = endX - initX;
        int sizeY = endY - initY;

        if (sizeX + initX > fixedBitmap.getWidth()) {
            sizeX = fixedBitmap.getWidth() - initX;
        }
        if (sizeY + initY > fixedBitmap.getHeight()) {
            sizeY = fixedBitmap.getHeight() - initY;
        }

        Bitmap croppedBitmap = Bitmap.createBitmap(fixedBitmap, initX, initY, sizeX, sizeY);
        if (fixedBitmap != croppedBitmap) {
            fixedBitmap.recycle();
        }

        return croppedBitmap;
    }
}
