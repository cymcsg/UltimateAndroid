/* 
 * HaoRan ImageFilter Classes v0.3
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


public class RaiseFrameFilter implements IImageFilter{

	int   _size ;
    

    public RaiseFrameFilter(int size)
    {
        _size = ((size >= 1) ? size : 1) ;
    }
  
    //@Override
    public Image process(Image imageIn) {
    	int r, g, b, a = 20;
		int width = imageIn.getWidth();
		int height = imageIn.getHeight();
		for(int x = 0 ; x < width ; x++){
			for(int y = 0 ; y < height; y++){
				r = imageIn.getRComponent(x, y);
				g = imageIn.getGComponent(x, y);
				b = imageIn.getBComponent(x, y);
				int cr ;
				if ((x < _size) && (y < height-x) && (y >= x))
					cr = Color.rgb(255,255,65) ; // left
				else if ((y < _size) && (x < width-y) && (x >= y))
					cr = Color.rgb(255,255,120) ; // top
				else if ((x >width-_size) && (y >= width-x) && (y < height+x-width))
					cr = Color.rgb(0,0,65) ; // right
				else if (y >height-_size)
					cr = Color.rgb(0,0,120) ; // bottom
				else
					continue;

				int colorR = cr & 0xFF0000 >> 16;
			    int colorG = cr & 0x00FF00 >> 8;
			    int colorB = cr & 0x0000FF;
				int t = 0xFF - a ;
				imageIn.setPixelColor(x, y, (colorR * a + r * t) / 0xFF, (colorG * a + g * t) / 0xFF, (colorB * a + b * t) / 0xFF);
			}
		}
        return imageIn;     
    }
}