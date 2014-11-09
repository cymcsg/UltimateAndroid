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

import android.app.Activity;

public class FillPatternFilter  implements IImageFilter{
	
	private Image pattern;
	
	private float Mixture = 0.2f;//0.5f
	
    public FillPatternFilter(Activity activity, int resourceId)
    {
        this.pattern = Image.LoadImage(activity, resourceId);
    }
    
    public FillPatternFilter(Activity activity, int resourceId, int Mixture)
    {
    	this.pattern = Image.LoadImage(activity, resourceId);
        this.Mixture = Mixture;
    }

    public Image process(Image imageIn)
    {
    	int mix1 = (int) (Mixture * 255f);
	    int mix2 = 255 - mix1;
        int r, g, b, r1, g1, b1;
        for (int x = 0; x < imageIn.getWidth(); x++)
        {
            for (int y = 0; y < imageIn.getHeight(); y++)
            {
                int xx = x % pattern.getWidth();
                int yy = y % pattern.getHeight();

                r = imageIn.getRComponent(x, y);
                g = imageIn.getGComponent(x, y);
                b = imageIn.getBComponent(x, y); 
                r1 = Image.SAFECOLOR(r + pattern.getRComponent(xx, yy));
                g1 = Image.SAFECOLOR(g + pattern.getGComponent(xx, yy));
                b1 = Image.SAFECOLOR(b + pattern.getBComponent(xx, yy));
                r = (r * mix2) + (r1 * mix1);
		        g = (g * mix2) + (g1 * mix1);
		        b = (b * mix2) + (b1 * mix1); 
		        imageIn.setPixelColor(x, y, r >> 8, g >> 8, b >> 8);
           }
        }
        return imageIn;
    }
}
