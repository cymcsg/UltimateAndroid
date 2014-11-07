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
 * �����Ч
 * @author daizhj
 *
 */
public class NoiseFilter implements IImageFilter{
	public float Intensity = 0.2f;
	 
    public static int getRandomInt(int a, int b) {  
    	int min = Math.min(a, b);    
    	int max = Math.max(a, b);
        return min + (int)(Math.random() * (max - min + 1));
    }  
    
    //@Override
    public Image process(Image imageIn) {
        int r, g, b, a;
        int num = (int)(this.Intensity * 32768f);
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
                 r = imageIn.getRComponent(x, y);
                 g = imageIn.getGComponent(x, y);
                 b = imageIn.getBComponent(x, y);
                 if (num != 0){
                    int rr = getRandomInt(-255, 0xff) * num;
                    int gg = getRandomInt(-255, 0xff) * num;
                    int bb = getRandomInt(-255, 0xff) * num;
                    int rrr = r + (rr >> 15);
                    int ggg = g + (gg >> 15);
                    int bbb = b + (bb >> 15);
                    r = (rrr > 0xff) ? ((byte)0xff) : ((rrr < 0) ? ((byte)0) : ((byte)rrr));
                    g = (ggg > 0xff) ? ((byte)0xff) : ((ggg < 0) ? ((byte)0) : ((byte)ggg));
                    b = (bbb > 0xff) ? ((byte)0xff) : ((bbb < 0) ? ((byte)0) : ((byte)bbb));
                 }              
                 imageIn.setPixelColor(x,y,r,g,b);
             }
        } 
	    return imageIn;
    }
}
