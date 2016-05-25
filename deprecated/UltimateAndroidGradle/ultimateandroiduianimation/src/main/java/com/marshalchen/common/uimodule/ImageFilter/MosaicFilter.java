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
 * �����Ч��
 * @author daizhj
 *
 */
public class MosaicFilter implements IImageFilter{
	 
	/**
	 * ����˴�С
	 */
	public int MosiacSize = 4;
	
    //@Override
    public Image process(Image imageIn) {
    	int width = imageIn.getWidth();
    	int height = imageIn.getHeight();
        int r = 0, g = 0, b = 0;
        for (int y = 0; y < height; y++) {
        	for (int x = 0; x < width; x++) {	
    	         if (y % MosiacSize == 0) { 	        	 
    	       		 if (x % MosiacSize == 0){//����ʱ��ȡ���ظ�ֵ                      	 
	       			     r = imageIn.getRComponent(x, y);
	    	             g = imageIn.getGComponent(x, y);
		                 b = imageIn.getBComponent(x, y);
		         	 }
    	       		 imageIn.setPixelColor(x, y, r, g, b);
    	       	 }
    	       	 else{ //������һ��          */ 	       		 
    	             imageIn.setPixelColor(x, y, imageIn.getPixelColor(x, y -1));
    	         }
    	    }
   	   }
       return imageIn;
    }
}
