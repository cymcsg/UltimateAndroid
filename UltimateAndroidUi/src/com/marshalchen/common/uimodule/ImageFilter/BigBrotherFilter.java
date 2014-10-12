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
 * �Զ�У��Ч��
 * @author daizhj
 *
 */
public class BigBrotherFilter implements IImageFilter {

    private static final int DOT_AREA = 10;
    private static final int arrDither[] = {        
            167,200,230,216,181,
            94,72,193,242,232,
            36,52,222,167,200,
            181,126,210,94,72,
            232,153,111,36,52,
            167,200,230,216,181,
            94,72,193,242,232,
            36,52,222,167,200,
            181,126,210,94,72,
            232,153,111,36,52,
            167,200,230,216,181,
            94,72,193,242,232,
            36,52,222,167,200,
            181,126,210,94,72,
            232,153,111,36,52,
            167,200,230,216,181,
            94,72,193,242,232,
            36,52,222,167,200,
            181,126,210,94,72,
            232,153,111,36,52
    };

    //@Override
    public Image process(Image imageIn) {
        for (int x = 0; x < imageIn.getWidth(); x+=DOT_AREA) {
            for (int y = 0; y < imageIn.getHeight(); y+=DOT_AREA) {
                    drawTone(x,y,imageIn);
            }
        }                
        return imageIn;
    }



    private void drawTone(int a_x, int a_y, Image imageIn){
        int l_grayIntensity;
        int l_x;
        int l_y;

        for(int x=0; x<DOT_AREA*DOT_AREA; x++){
            l_x = x%DOT_AREA;
            l_y = x/DOT_AREA;

            if(a_x+l_x < imageIn.getWidth() && a_y+l_y < imageIn.getHeight()){

                l_grayIntensity = 255-(imageIn.getRComponent(a_x+l_x, a_y+l_y));
                if(l_grayIntensity > arrDither[x]){
                    imageIn.setPixelColor(a_x+l_x, a_y+l_y, 0,0,0);
                }
                else{
                    imageIn.setPixelColor(a_x+l_x, a_y+l_y, 255,255,255);
                }
            }
        }
    }

}

