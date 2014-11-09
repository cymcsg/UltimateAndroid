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
 * ɫ�ɱ��Ͷ���Ч
 * @author daizhj
 *
 */
public class SaturationModifyFilter implements IImageFilter{

	public float SaturationFactor = 0.5f;
    //@Override
    public Image process(Image imageIn) {
    	float saturation = this.SaturationFactor + 1f;
    	float negosaturation = 1f - saturation;

        int r, g, b, a;
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
            	 r = imageIn.getRComponent(x, y);
                 g = imageIn.getGComponent(x, y);
                 b = imageIn.getBComponent(x, y);  
                 
                 float nego1 = negosaturation * 0.2126f;
                 float ngeo2 = nego1 + saturation;
                 float ngeo3 = negosaturation * 0.7152f;
                 float nego4 = ngeo3 + saturation;
                 float nego5 = negosaturation * 0.0722f;
                 float nego6 = nego5 + saturation;
                 float nego7 = ((r * ngeo2) + (g * ngeo3)) + (b * nego5);
                 float nego8 = ((r * nego1) + (g * nego4)) + (b * nego5);
                 float nego9 = ((r * nego1) + (g * ngeo3)) + (b * nego6);
                 r = (nego7 > 255f) ? 255 : ((nego7 < 0f) ? 0 : ((int) nego7));
                 g = (nego8 > 255f) ? 255 : ((nego8 < 0f) ? 0 : ((int) nego8));
                 b = (nego9 > 255f) ? 255 : ((nego9 < 0f) ? 0 : ((int) nego9));
                 imageIn.setPixelColor(x,y,r,g,b);
             }
        } 
        return imageIn;
    }

}
