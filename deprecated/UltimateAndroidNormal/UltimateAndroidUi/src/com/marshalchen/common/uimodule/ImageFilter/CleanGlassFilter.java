/* 
 * HaoRan ImageFilter Classes v0.2
 * Copyright (C) 2012 Zhenjun Dai
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation.
 */

package com.marshalchen.common.uimodule.ImageFilter;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.marshalchen.common.uimodule.ImageFilter.ImageBlender.BlendMode;
import android.util.Log;

/**
 * ��ɨ����Ч��
 * @author daizhj
 *
 */
public class CleanGlassFilter implements IImageFilter{
	/// <summary>
    /// Should be in the range [0, 1].
    /// </summary>
    public float Size = 0.5f;

    public CleanGlassFilter()
    {
        Size = 0.5f;
    }

    //@Override
    public Image process(Image imageIn)
    {
        int width = imageIn.getWidth();
        int height = imageIn.getHeight();
        Image clone = imageIn.clone();
        int r = 0, g = 0, b = 0;

        int ratio = imageIn.getWidth() > imageIn.getHeight() ? imageIn.getHeight() * 32768 / imageIn.getWidth() : imageIn.getWidth() * 32768 / imageIn.getHeight();

        // Calculate center, min and max
        int cx = imageIn.getWidth() >> 1;
        int cy = imageIn.getHeight() >> 1;
        int max = cx * cx + cy * cy;
        int min = (int)(max * (1 - Size));
        int diff = max - min;

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                // Calculate distance to center and adapt aspect ratio
                int dx = cx - x;
                int dy = cy - y;
                if (imageIn.getWidth() > imageIn.getHeight())
                {
                    dy = (dy * ratio) >> 14;
                }
                else
                {
                    dx = (dx * ratio) >> 14;
                }
                int distSq = dx * dx + dy * dy;

                if (distSq > min)
                {
                    int k = NoiseFilter.getRandomInt(1, 123456);
                    //���ؿ��С
                    int pixeldx = x + k % 19;
                    int pixeldy = y + k % 19;
                    if (pixeldx >= width)
                    {
                        pixeldx = width - 1;
                    }
                    if (pixeldy >= height)
                    {
                        pixeldy = height - 1;
                    }
                    r = clone.getRComponent(pixeldx, pixeldy);
                    g = clone.getGComponent(pixeldx, pixeldy);
                    b = clone.getBComponent(pixeldx, pixeldy);
                    imageIn.setPixelColor(x, y, r, g, b);
                }
            }
        }
        return imageIn;
    }

}
