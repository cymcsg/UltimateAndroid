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
 * ��ľЧ�����ڴ�������
 * @author daizhj
 *
 */
public class BrickFilter implements IImageFilter{

	public int ThreshHold = 128;
  
    //@Override
    public Image process(Image imageIn) {
       int width = imageIn.getWidth();
       int height = imageIn.getHeight();
       Image clone = imageIn.clone();
       int r = 0, g = 0, b = 0, avg = 0;
       for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	  r = clone.getRComponent(x, y);
            	  g = clone.getGComponent(x, y);
            	  b = clone.getBComponent(x, y);
            	  avg = (r + g + b) / 3;
            	  avg = avg >= ThreshHold ? 255 : 0;
	        	  imageIn.setPixelColor(x, y, avg, avg, avg);
             }
        } 
        return imageIn;     
    }
}