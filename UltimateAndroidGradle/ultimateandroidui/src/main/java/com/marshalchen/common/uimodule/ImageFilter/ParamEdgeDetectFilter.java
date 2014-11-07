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
 * �����Ե���Ч��
 * @author daizhj
 *
 */
public class ParamEdgeDetectFilter  extends ConvolutionFilter{
	public boolean DoGrayConversion = true;
	public boolean DoInversion = true;
    public float Threshold = 0.25f;
    public float K00 = 1f;
    public float K01 = 2f;
    public float K02 = 1f;
	
    private Image ProcessColor(int k00, int k01, int k02, int k20, int k21, int k22, Image imageIn, int thresholdSq)
    {
    	 int width = imageIn.getWidth();
         int height = imageIn.getHeight();
         int r, g, b;
         Image clone = imageIn.clone();
         for (int x = 0; x < width; x++)
         {
 	        for (int y = 0; y < height; y++)
 	        {
                 int color1 = GetPixelColor(clone, x - 1, y - 1, width, height);
                 int color2 = GetPixelColor(clone, x, y - 1, width, height);
                 int color3 = GetPixelColor(clone, x + 1, y - 1, width, height);
                 int color4 = GetPixelColor(clone, x - 1, y, width, height);
                 int color5 = GetPixelColor(clone, x + 1, y, width, height);
                 int color6 = GetPixelColor(clone, x - 1, y + 1, width, height);
                 int color7 = GetPixelColor(clone, x, y + 1, width, height);
                 int color8 = GetPixelColor(clone, x + 1, y + 1, width, height);
                 
                 int color1RGB = (0x00FF0000 & color1) >> 16;
                 int color3RGB = (0x00FF0000 & color3) >> 16;
                 int color6RGB = (0x00FF0000 & color6) >> 16;
                 int color8RGB = (0x00FF0000 & color8) >> 16;           
                 int colorSum1 = (color1RGB * k00 + color3RGB * k02 + ((0x00FF0000 & color2) >> 16) * k01 + color6RGB * k20 + ((0x00FF0000 & color7) >> 16) * k21 + color8RGB * k22) >> 8;
                 int colorSum2 = (color1RGB * k00 + color3RGB * k20 + ((0x00FF0000 & color4) >> 16) * k01 + color6RGB * k02 + ((0x00FF0000 & color5) >> 16) * k21 + color8RGB * k22) >> 8;
                 r = (((colorSum1 * colorSum1) + (colorSum2 * colorSum2)) > thresholdSq) ? 0 : 0xff;
                 if (this.DoInversion) {
                     r = 255 - r;
                 }
                 
                 color1RGB = (0x0000FF00 & color1) >> 8;
                 color3RGB = (0x0000FF00 & color3) >> 8;
                 color6RGB = (0x0000FF00 & color6) >> 8;
                 color8RGB = (0x0000FF00 & color8) >> 8;
                 colorSum1 = (color1RGB * k00 + color3RGB * k02 + ((0x0000FF00 & color2) >> 8) * k01 + color6RGB * k20 + ((0x0000FF00 & color7) >> 8) * k21 + color8RGB * k22) >> 8;
                 colorSum2 = (color1RGB * k00 + color3RGB * k20 + ((0x0000FF00 & color4) >> 8) * k01 + color6RGB * k02 + ((0x0000FF00 & color5) >> 8) * k21 + color8RGB * k22) >> 8;
                 g = (((colorSum1 * colorSum1) + (colorSum2 * colorSum2)) > thresholdSq) ? 0 : 0xff;
                 if (this.DoInversion){
                     g = 255 - g;
                 }
                 
                 color1RGB = 0x000000FF & color1;
                 color3RGB = 0x000000FF & color3;
                 color6RGB = 0x000000FF & color6;
                 color8RGB = 0x000000FF & color8;
                 colorSum1 = (color1RGB * k00 + color3RGB * k02 + (0x000000FF & color2) * k01 + color6RGB * k20 + (0x000000FF & color7) * k21 + color8RGB * k22) >> 8;
                 colorSum2 = (color1RGB * k00 + color3RGB * k20 + (0x000000FF & color4) * k01 + color6RGB * k02 + (0x000000FF & color5) * k21 + color8RGB * k22) >> 8;
                 b = (((colorSum1 * colorSum1) + (colorSum2 * colorSum2)) > thresholdSq) ? 0 : 0xff;
                 if (DoInversion) {
                     b = 255 - b;
                 }  
                 imageIn.setPixelColor(x,y,r,g,b);
             }
         }
         return imageIn;
    }


    private Image ProcessGray(int k00, int k01, int k02, int k20, int k21, int k22, Image imageIn, int thresholdSq)
    {
        int width = imageIn.getWidth();
        int height = imageIn.getHeight();
        Image clone = imageIn.clone();
        for (int x = 0; x < width; x++)
        {
	        for (int y = 0; y < height; y++)
	        {    
                int color1 = GetPixelBrightness(clone, x - 1, y - 1, width, height);
                int color2 = GetPixelBrightness(clone, x, y - 1, width, height);
                int color3 = GetPixelBrightness(clone, x + 1, y - 1, width, height);
                int color4 = GetPixelBrightness(clone, x - 1, y, width, height);
                int color5 = GetPixelBrightness(clone, x + 1, y, width, height);
                int color6 = GetPixelBrightness(clone, x - 1, y + 1, width, height);
                int color7 = GetPixelBrightness(clone, x, y + 1, width, height);
                int color8 = GetPixelBrightness(clone, x + 1, y + 1, width, height);
                int colorSum1 = (color1 * k00 +  color2 * k01 + color3 * k02 + color6 * k20 + color7 * k21 + color8 * k22) >> 8;             
                int colorSum2 = (color1 * k00 + color3 * k20 + color4 * k01 + color5 * k21 + color6 * k02 + color8 * k22) >> 8;
                int rgb = (((colorSum1 * colorSum1) + (colorSum2 * colorSum2)) > thresholdSq) ? 0 : 0xff;
                if (DoInversion){
                    rgb = 0xff - rgb;
                }
                imageIn.setPixelColor(x,y,rgb,rgb,rgb);
            }
        }
        return imageIn;
    }

    //@Override
    public Image process(Image imageIn) {
    	int k00 = (int) (K00 * 255f);
	    int k01 = (int) (K01 * 255f);
	    int k02 = (int) (K02 * 255f);
	    int thresholdSqFactor = (int) (Threshold * 255f * 2f);
	    int thresholdSq = thresholdSqFactor * thresholdSqFactor;
	    
	    if (!DoGrayConversion){
	        return ProcessColor(k00, k01, k02, -k00, -k01, -k02, imageIn.clone(), thresholdSq);
	    }
	    return ProcessGray(k00, k01, k02, -k00, -k01, -k02, imageIn, thresholdSq);	      
    }
}
