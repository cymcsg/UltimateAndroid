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

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public class Gradient {
	 public List<Integer> MapColors;
	 
	    public Gradient()
	    {
	        List<Integer> list = new ArrayList<Integer>();
	        list.add(Color.BLACK);
	        list.add(Color.WHITE);
	        this.MapColors = list;
	    }

	    public Gradient(List<Integer> colors)
	    {
	        this.MapColors = colors;
	    }

	    
	    private Palette CreateGradient(List<Integer> colors, int length)
	    {
	        if (colors == null || colors.size() < 2){
	        	return null;
	        }

	         Palette palette = new Palette(length);
	         int[] red = palette.Red;
	         int[] green = palette.Green;
	         int[] blue = palette.Blue;
	         int num = length / (colors.size() - 1);
	         float num1 = 1f / ((float)num);
	         int index = 0;
	         int rgb = colors.get(0);
	         int colorR = (rgb & 0x00FF0000) >> 16;
	         int colorG = (rgb & 0x0000FF00) >> 8;
	         int colorB =  rgb & 0x000000FF;
	         for (int i = 1; i < colors.size(); i++){
	             int r = (colors.get(i) & 0x00FF0000) >> 16;
	             int g = (colors.get(i) & 0x0000FF00) >> 8;
	             int b =  colors.get(i) & 0x000000FF;
	             for (int j = 0; j < num; j++) {
	                 float num2 = j * num1;
	                 int rr = colorR + ((int)((r - colorR) * num2));
	                 int gg = colorG + ((int)((g - colorG) * num2));
	                 int bb = colorB + ((int)((b - colorB) * num2)); 
	                 red[index]  = (rr > 0xff ? 0xff : ((rr < 0) ? 0 : rr));
	                 green[index]= (gg > 0xff ? 0xff : ((gg < 0) ? 0 : gg));
	                 blue[index] = (bb > 0xff ? 0xff : ((bb < 0) ? 0 : bb));
	                 index++;
	             }
	             colorR = r;
	             colorG = g;
	             colorB = b;
	         }
	         if (index < length) {
	             red[index] = red[index - 1];
	             green[index] = green[index - 1];
	             blue[index] = blue[index - 1];
	         }
	         return palette;
	    }

	    public Palette CreatePalette(int length)
	    {
	        return CreateGradient(this.MapColors, length);
	    }

	    public static class TintColors
	    {
	        public static int LightCyan() {   
	            return (0xff << 24) + (0xeb << 16) + (0xf5 << 8) + 0xe1;
	        }
	        
	        public static int Sepia(){
	            return (0xff << 24) + (179 << 16) + (179 << 8) + 230;
	        }
	    }

	    public static Gradient BlackSepia()
	    {
         List<Integer> colors = new ArrayList<Integer>();
         colors.add(Color.BLACK);
         colors.add(TintColors.Sepia());
         return new Gradient(colors);
	    }
	 
	    public static Gradient WhiteSepia()
	    {
         List<Integer> colors = new ArrayList<Integer>();   
         colors.add(Color.WHITE);
         colors.add(TintColors.Sepia());
         return new Gradient(colors);
	    }

	    public static Gradient RainBow()
	    {
         List<Integer> colors = new ArrayList<Integer>();
         colors.add(Color.RED);
         colors.add(Color.MAGENTA);
         colors.add(Color.BLUE);
         colors.add(Color.CYAN);
         colors.add(Color.GREEN);
         colors.add(Color.YELLOW);
         colors.add(Color.RED);
         return new Gradient(colors);
	    }
	    
	    public static Gradient Inverse()
	    {
	         List<Integer> colors = new ArrayList<Integer>();
	         colors.add(Color.WHITE);
	         colors.add(Color.BLACK);
	         return new Gradient(colors);
	    }
	    
	    public static Gradient Fade()
        {
            List<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.BLACK);
            colors.add(Color.rgb(0xCD, 0xE8, 0xEE));//Cornsilk2  , reference http://www.wescn.com/tool/color_3.html
            colors.add(Color.BLACK);
            return new Gradient(colors);
        }

        public static Gradient Scene()
        {
            List<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.rgb(0x00, 0xD7, 0xFF));//Gold  , reference http://www.wescn.com/tool/color_3.html
            colors.add(Color.WHITE);
            colors.add(Color.rgb(0x00, 0xD7, 0xFF));//Gold
            return new Gradient(colors);
        }

        public static Gradient Scene1()
        {
            List<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.rgb(0xED, 0x95, 0x64));//CornflowerBlue  , reference http://www.wescn.com/tool/color_3.html
            colors.add(Color.WHITE);
            colors.add(Color.rgb(0xED, 0x95, 0x64));//CornflowerBlue
            return new Gradient(colors);
        }

       
        public static Gradient Scene2()
        {
            List<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.rgb(0xFF, 0xBF, 0x00));//DeepSkyBlue  , reference http://www.wescn.com/tool/color_3.html
            colors.add(Color.WHITE);
            colors.add(Color.rgb(0xFF, 0xBF, 0x00));//DeepSkyBlue
            return new Gradient(colors);
        }

        public static Gradient Scene3()
        {
            List<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.rgb(0x00, 0xa5, 0xff));// , reference http://www.wescn.com/tool/color_3.html
            colors.add(Color.WHITE);
            colors.add(Color.rgb(0x00, 0xa5, 0xff));//
            return new Gradient(colors);
        }

}
