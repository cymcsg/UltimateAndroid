/* 
 * HaoRan ImageFilter Classes v0.1
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

/**
 * ��ֵ��Ч
 * @author daizhj
 *
 */
public class ThresholdFilter implements IImageFilter{

	private float Threshold = 0.5f;
    //@Override
    public Image process(Image imageIn) {
        int r, g, b;
        int threshold = (int)(this.Threshold * 255f);

        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
                 r = imageIn.getRComponent(x, y);
                 g = imageIn.getGComponent(x, y);
                 b = imageIn.getBComponent(x, y);

                 int rgb = (((r * 0x1b36) + (g * 0x5b8c)) + (b * 0x93e)) >> 15;
            	 r = g = b  = rgb > threshold ? 0xff : 0;
                 imageIn.setPixelColor(x,y,r,g,b);
             }
        } 
        return imageIn;
    }

}
