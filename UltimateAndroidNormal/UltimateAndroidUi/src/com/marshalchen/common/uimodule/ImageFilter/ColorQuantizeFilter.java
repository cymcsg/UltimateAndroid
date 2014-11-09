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
 * ��ɫ������Ч
 * @author daizhj
 */
public class ColorQuantizeFilter implements IImageFilter{

	private float levels = 5f;

    //@Override
    public Image process(Image imageIn) {
        int r, g, b, a;
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
                 r = imageIn.getRComponent(x, y);
                 g = imageIn.getGComponent(x, y);
                 b = imageIn.getBComponent(x, y);
                 float quanR = (((float) ((int) (r * 0.003921569f * levels))) / levels) * 255f;
                 float quanG = (((float) ((int) (g * 0.003921569f * levels))) / levels) * 255f;
                 float quanB = (((float) ((int) (b * 0.003921569f * levels))) / levels) * 255f;
                 r = (quanR > 255f) ? 255 : ((quanR < 0f) ? 0 : ((int)quanR));
                 g = (quanG > 255f) ? 255 : ((quanG < 0f) ? 0 : ((int) quanG));
                 b = (quanB > 255f) ? 255 : ((quanB < 0f) ? 0: ((int) quanB));
                 imageIn.setPixelColor(x,y,r,g,b);
             }
        } 
        return imageIn;
    }

}
