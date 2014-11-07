/* 
 * HaoRan ImageFilter Classes v0.4
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

public class HslModifyFilter implements IImageFilter{
	private float HueFactor;//ɫ��
    
    /// <summary>
    /// initial value setting reference to http://blog.csdn.net/yacper/article/details/4743014
    /// </summary>
    /// <param name="HueFactor"></param>
    public HslModifyFilter(float HueFactor)
    {
        this.HueFactor = Math.max(0, Math.min(359, HueFactor));
    }

  

    public Image process(Image imageIn)
    {
        int r, g, b;
        HslColor hsl = new HslColor(HueFactor, 0, 0);
      
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
         	    r = imageIn.getRComponent(x, y);
                g = imageIn.getGComponent(x, y);
                b = imageIn.getBComponent(x, y);

                HslColor.RgbToHsl(r, g, b, hsl);
                hsl.h = this.HueFactor;
                int color = HslColor.HslToRgb(hsl);
                imageIn.setPixelColor(x, y, color);
            }
        }
        return imageIn;
    }
}
