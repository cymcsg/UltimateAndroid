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
 * ������Ч
 * @author daizhj
 *
 */
public class ReliefFilter implements IImageFilter{

    //@Override
    public Image process(Image imageIn) {
       for (int x = 0; x < (imageIn.getWidth() - 1); x++) {
	        for (int y = 0; y < imageIn.getHeight(); y++) {
		        int rr = imageIn.getRComponent(x, y) - imageIn.getRComponent(x + 1, y) + 128;
		        int gg = imageIn.getGComponent(x, y) - imageIn.getGComponent(x + 1, y) + 128; 
		        int bb = imageIn.getBComponent(x, y) - imageIn.getBComponent(x + 1, y) + 128;
		        //�������
		        if (rr > 255) rr = 255; 
		        if (rr < 0) rr = 0; 
		        if (gg > 255) gg = 255;
		        if (gg < 0) gg = 0;
		        if (bb > 255) bb = 255;
		        if (bb < 0) bb = 0;  
		        
		        imageIn.setPixelColor(x, y, rr, gg, bb);
	         }
        } 
        return imageIn;
    }
}
