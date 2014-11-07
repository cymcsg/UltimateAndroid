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
 * �ͻ�Ч��
 * @author daizhj
 *
 */
public class OilPaintFilter implements IImageFilter{

	public int Model = 3;
    
    //@Override
    public Image process(Image imageIn) {
       int width = imageIn.getWidth();
       int height = imageIn.getHeight();
       Image clone = imageIn.clone();
       int r = 0, g = 0, b = 0, xx = 0, yy = 0;
       for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	  int pos = NoiseFilter.getRandomInt(1, 10000) % Model;
            	  xx = (x + pos) < width ? (x + pos) : (x - pos) >= 0 ? (x - pos) : x;
            	  yy = (y + pos) < height ? (y + pos) : (y - pos) >= 0 ? (y - pos) : y;  
            	  r = clone.getRComponent(xx, yy);
            	  g = clone.getGComponent(xx, yy);
            	  b = clone.getBComponent(xx, yy);
	        	  imageIn.setPixelColor(x, y, r, g, b);
             }
        } 
        return imageIn;     
    }
}