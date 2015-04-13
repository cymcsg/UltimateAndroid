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

public class PixelateFilter implements IImageFilter {
    
    /**
     * Size of the blurred pixel - the bigger this is, the more
     * coarsely the image will be pixelated. The pixelation appearence
     * will always be different for different size images
     */
    private int pixelSize = 4;

    //@Override
    public Image process(Image imageIn) {
        int l_rgb;    
        for (int x = 0; x < imageIn.getWidth(); x+=pixelSize) {
            for (int y = 0; y < imageIn.getHeight(); y+=pixelSize) {                                
                    l_rgb = getPredominantRGB(imageIn, x,y,pixelSize);
                    fillRect(imageIn, x,y,pixelSize, l_rgb);                                        
            }
        }
        
        return imageIn;
    }
    
    
    
    /**
     * @return the pixelSize
     */
    public int getPixelSize() {
        return pixelSize;
    }



    /**
     * @param pixelSize the pixelSize to set
     */
    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
    }



    /**
     * Method gets the predominant colour pixels to extrapolate
     * the pixelation from
     * 
     * @param imageIn
     * @param a_x
     * @param a_y
     * @param squareSize
     * @return
     */
    private int getPredominantRGB(Image imageIn, int a_x, int a_y, int squareSize){
        int red=-1;
        int green=-1;
        int blue=-1;
        
        for(int x=a_x; x<a_x+squareSize; x++){
            for(int y=a_y; y<a_y+squareSize; y++){
                if(x < imageIn.getWidth() && y < imageIn.getHeight()){            
                    if(red == -1){
                        red = imageIn.getRComponent(x,y);
                    }
                    else{
                        red = (red+imageIn.getRComponent(x,y))/2;
                    }
                    if(green == -1){
                        green = imageIn.getGComponent(x,y);
                    }
                    else{
                        green = (green+imageIn.getGComponent(x,y))/2;
                    }
                    if(blue == -1){
                        blue = imageIn.getBComponent(x,y);
                    }
                    else{
                        blue = (blue+imageIn.getBComponent(x,y))/2;     
                    }
                }                               
            } 
        }
        return (255<<24)+(red<<16)+(green<<8)+blue;
    }
    
    /**
     * Method to extrapolate out
     * 
     * @param imageIn
     * @param a_x
     * @param a_y
     * @param squareSize
     * @param a_rgb
     */
    private void fillRect(Image imageIn, int a_x, int a_y, int squareSize, int a_rgb){
        for(int x=a_x; x<a_x+squareSize; x++){
            for(int y=a_y; y<a_y+squareSize; y++){
                if(x < imageIn.getWidth() && y < imageIn.getHeight()){
                    imageIn.setPixelColor(x,y,a_rgb);
                }
            }
        }                                       
    }

}
