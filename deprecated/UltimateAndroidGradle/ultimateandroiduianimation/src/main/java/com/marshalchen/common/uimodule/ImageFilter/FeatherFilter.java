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
 * ��Ч��
 * @author daizhj
 *
 */
public class FeatherFilter  implements IImageFilter{

	/// <summary>
    /// Should be in the range [0, 10].
    /// </summary>
    public float Size = 0.5f;
    
    //@Override
    public Image process(Image imageIn) {
    	 int r, g, b;
    	 int width = imageIn.getWidth();
    	 int height = imageIn.getHeight();
    	 int ratio = width >  height ?  height * 32768 / width : width * 32768 /  height;

         // Calculate center, min and max
         int cx = width >> 1;
         int cy = height >> 1;
         int max = cx * cx + cy * cy;
         int min = (int)(max * (1 - Size));
         int diff = max - min;

         for (int x = 0; x < width; x++) {
             for (int y = 0; y < height; y++) {
             	  r = imageIn.getRComponent(x, y);
                  g = imageIn.getGComponent(x, y);
                  b = imageIn.getBComponent(x, y);

                  // Calculate distance to center and adapt aspect ratio
                  int dx = cx - x;
                  int dy = cy - y;
                  if (width > height){
                     dx = (dx * ratio) >> 15;
                  }
                  else{
                     dy = (dy * ratio) >> 15;
                  }
                  int distSq = dx * dx + dy * dy;
            	  float v =  ((float)distSq / diff) * 255;
                  r = (int)(r + (v));
                  g = (int)(g + (v));
                  b = (int)(b + (v));
                  r = (r > 255 ? 255 : (r < 0 ? 0 : r));
                  g = (g > 255 ? 255 : (g < 0 ? 0 : g));
                  b = (b > 255 ? 255 : (b < 0 ? 0 : b));
                  imageIn.setPixelColor(x,y,r,g,b);
              }
         } 
         return imageIn;
    }

}
