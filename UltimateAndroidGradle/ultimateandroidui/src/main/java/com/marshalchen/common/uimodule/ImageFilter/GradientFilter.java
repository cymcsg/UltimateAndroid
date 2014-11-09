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

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public class GradientFilter  implements IImageFilter{
	
	
    private Palette palette = null;
    public Gradient Gradient;
    public float OriginAngleDegree;
  
    // Methods
    public GradientFilter()
    {
        this.OriginAngleDegree = 0f;
        this.Gradient = new Gradient();
    }

    public void ClearCache()
    {
        this.palette = null;
    }

    //@Override
    public Image process(Image imageIn) {
    	int width = imageIn.getWidth();
    	int height = imageIn.getHeight();
        double d = this.OriginAngleDegree * 0.0174532925;
        float cos = (float)Math.cos(d);
        float sin = (float)Math.sin(d);
        float radio = (cos * width) + (sin * height);
        float dcos = cos * radio;
        float dsin = sin * radio;
        int dist = (int)Math.sqrt((double)((dcos * dcos) + (dsin * dsin)));
        dist = Math.max(Math.max(dist, width), height);
   
        if ((this.palette == null) || (dist != this.palette.Length)) {
            this.palette = this.Gradient.CreatePalette(dist);
        }
        int[] red = this.palette.Red;
        int[] green = this.palette.Green;
        int[] blue = this.palette.Blue;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
            	radio = (cos * j) + (sin * i);
                dcos = cos * radio;
                dsin = sin * radio;
                dist = (int)Math.sqrt((double)((dcos * dcos) + (dsin * dsin)));
                imageIn.setPixelColor(j, i, red[dist], green[dist], blue[dist]);
            }
        }
        return imageIn;
    } 
}

