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

public class MonitorFilter implements IImageFilter {

    //@Override
    public Image process(Image imageIn) {
        int r,g,b;
        for (int x = 0; x < imageIn.getWidth(); x++) {                  
            for (int y = 0; y < imageIn.getHeight(); y+=3) {                
                r=0;
                g=0;
                b=0;                 
                for(int w=0; w<3; w++){
                    if(y+w < imageIn.getHeight() ){
                        r += (imageIn.getRComponent(x, y+w))/2;
                        g += (imageIn.getGComponent(x, y+w))/2;
                        b += (imageIn.getBComponent(x, y+w))/2;                                         
                    }
                }
                r = getValidInterval(r);
                g = getValidInterval(g);
                b = getValidInterval(b);
                                
                for(int w=0; w<3; w++){
                    if(y+w < imageIn.getHeight()){
                        if(w == 0){
                            imageIn.setPixelColor(x,y+w,r,0,0);
                        }
                        else if(w ==1){
                            imageIn.setPixelColor(x,y+w,0,g,0);
                        }
                        else if(w==2){
                            imageIn.setPixelColor(x,y+w,0,0,b);
                        }
                    }
                }                               
            }
        }    
        return imageIn;
    }
    
    
    /**
     * method to calculate an appropriate interval for flicker lines
     * 
     * @param a_value
     * @return
     */
    public int getValidInterval(int a_value){
        if(a_value < 0) return 0;
        if(a_value > 255) return 255;
        return a_value;
    }

}
