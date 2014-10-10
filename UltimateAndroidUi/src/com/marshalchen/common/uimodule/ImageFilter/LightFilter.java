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
 * ����Ч��
 * @author daizhj
 *
 */
public class LightFilter extends RadialDistortionFilter{

	/**
	 * ��������
	 */
	public float Light = 150.0f;
    
    //@Override
    public Image process(Image imageIn) {
       int width = imageIn.getWidth();
       int halfw = width / 2;
       int height = imageIn.getHeight();
       int halfh = height / 2;
       int R = Math.min(halfw, halfh);
       //���ĵ㣬������ֵ����ǿ�����ķ���ƫ��
       Point point = new Point(halfw, halfh);
       int r = 0, g = 0, b = 0;
       for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	 float length = (float)Math.sqrt(Math.pow((x -point.X), 2) + Math.pow((y -point.Y), 2)); 
            	 r = imageIn.getRComponent(x, y);
            	 g = imageIn.getGComponent(x, y);
            	 b = imageIn.getBComponent(x, y);
            	 //λ�ڹ�����
            	 if(length < R) {
            		  float pixel = Light * (1.0f - length /R);
	            	  r = r + (int)pixel;
	            	  r = Math.max(0, Math.min(r, 255));
	            	  g = g + (int)pixel;
	            	  g = Math.max(0, Math.min(g, 255));
	            	  b = b + (int)pixel;
	            	  b = Math.max(0, Math.min(b, 255));          	  
            	 }	
            	 imageIn.setPixelColor(x, y, r, g, b);
             }
        } 
        return imageIn;     
    }
}