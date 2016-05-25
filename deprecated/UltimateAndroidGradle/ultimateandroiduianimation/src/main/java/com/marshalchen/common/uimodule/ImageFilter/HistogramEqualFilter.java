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
 * ֱ��ͼ��Ч
 * @author daizhj
 *
 */
public class HistogramEqualFilter implements IImageFilter{

public float ContrastIntensity = 1f;
    
	//@Override
    public Image process(Image imageIn) {
        int r, g, b;
        int[] array = new int[256];
        int[] numArray = new int[imageIn.getHeight()*imageIn.getWidth()];
        int contrast = (int) (this.ContrastIntensity * 255f);
        int pos = 0;
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
            	 r = imageIn.getRComponent(x, y);
                 g = imageIn.getGComponent(x, y);
                 b = imageIn.getBComponent(x, y);
                 int index = (r * 0x1b36 + g * 0x5b8c + b * 0x93e) >> 15;
            	 array[index]++;
                 numArray[pos] = index;
                 pos++;
            }
        }
        for (int i = 1; i < 0x100; i++){
            array[i] += array[i - 1];
        }
        for (int i = 0; i < 0x100; i++){
            array[i] = (array[i] << 8) / imageIn.getHeight()*imageIn.getWidth();
            array[i] = ((contrast * array[i]) >> 8) + (((0xff - contrast) * i) >> 8);
        }
        pos = 0;
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
            	 r = imageIn.getRComponent(x, y);
                 g = imageIn.getGComponent(x, y);
                 b = imageIn.getBComponent(x, y);
                 if (numArray[pos] != 0){
                     int num = array[numArray[pos]];
                     r = (r * num) / numArray[pos];
                     g = (g * num) / numArray[pos];
                     b = (b * num) / numArray[pos];
                     r = (r > 0xff) ? ((byte) 0xff) : ((r < 0) ? ((byte) 0) : ((byte) r));
                     g = (g > 0xff) ? ((byte) 0xff) : ((g < 0) ? ((byte) 0) : ((byte) g));
                     b = (b > 0xff) ? ((byte) 0xff) : ((b < 0) ? ((byte) 0) : ((byte) b));
                 }    
                 imageIn.setPixelColor(x,y,r,g,b);
                 pos++;
            }
        } 
        return imageIn;
    }
}
