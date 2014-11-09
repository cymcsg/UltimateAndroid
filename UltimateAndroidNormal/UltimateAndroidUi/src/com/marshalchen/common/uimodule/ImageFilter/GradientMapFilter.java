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

import android.graphics.Color;

public class GradientMapFilter  implements IImageFilter{
	
	public GradientMapFilter()
	{
		this.Map = new Gradient();
	}


	public GradientMapFilter(Gradient gradient)
	{
	    this.Map = gradient;
	    this.BrightnessFactor = this.ContrastFactor = 0f;
	}

	public Gradient Map;
	public float BrightnessFactor;
	public float ContrastFactor;

	//@Override
    public Image process(Image imageIn)
    {
    	Palette palette = this.Map.CreatePalette(0x100);
	    int[] red = palette.Red;
	    int[] green = palette.Green;
	    int[] blue = palette.Blue;
	    Image bitmap = imageIn.clone();
	    bitmap.clearImage(Color.WHITE);
	    int bfactor = (int) (this.BrightnessFactor * 255f);
	    float cfactor = 1f + this.ContrastFactor;
	    cfactor *= cfactor;
	    int limit = ((int) (cfactor * 32768f)) + 1;
	    for (int i = 0; i < imageIn.colorArray.length; i++)
	    {
	        int r = (imageIn.colorArray[i]& 0x00FF0000) >>> 16;
	        int g = (imageIn.colorArray[i]& 0x0000FF00) >>> 8;
	        int b = imageIn.colorArray[i]& 0x000000FF;
	        int index = (((r * 0x1b36) + (g * 0x5b8c)) + (b * 0x93e)) >> 15;
	        if (bfactor != 0)
	        {
	            index += bfactor;
	            index = (index > 0xff) ? 0xff : ((index < 0) ? 0 : index);
	        }
	        if (limit != 0x8001)
	        {
	            index -= 0x80;
	            index = (index * limit) >> 15;
	            index += 0x80;
	            index = (index > 0xff) ? 0xff : ((index < 0) ? 0 : index);
	        }
	        bitmap.colorArray[i] = (0xff << 24) + (red[index] << 16) + (green[index] << 8) + blue[index];
	    }
	    return bitmap;   	
    }
}
