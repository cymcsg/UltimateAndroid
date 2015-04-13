/*
 * Copyright 2014 ignasi
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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ignasi on 28/03/14.
 */
public class BitmapUtils {
    public static Bitmap forceEvenBitmapSize(Bitmap original) {
        int width = original.getWidth();
        int height = original.getHeight();

        if (width % 2 == 1) {
            width++;
        }
        if (height % 2 == 1) {
            height++;
        }

        Bitmap fixedBitmap = original;
        if (width != original.getWidth() || height != original.getHeight()) {
            fixedBitmap = Bitmap.createScaledBitmap(original, width, height, false);
        }

        if (fixedBitmap != original) {
            original.recycle();
        }

        return fixedBitmap;
    }

    public static Bitmap forceConfig565(Bitmap original) {
        Bitmap convertedBitmap = original;
        if (original.getConfig() != Bitmap.Config.RGB_565) {
            convertedBitmap = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(convertedBitmap);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawBitmap(original, 0, 0, paint);

            if (convertedBitmap != original) {
                original.recycle();
            }
        }

        return convertedBitmap;
    }
}
