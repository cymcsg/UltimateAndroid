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
 * Ť��Ч��
 * @author daizhj
 *
 */
public class RadialDistortionFilter  implements IImageFilter{

	public static class Point
	{
	   public float X;
	   public float Y;
	   public Point(float x, float y){
		   X = x;
		   Y = y;
	   }
	}
    public float Radius = 0.5f;
    public float Distortion = 1.5f;
    public Point Center = new Point(0.5f, 0.5f);
    
    //@Override
    public Image process(Image imageIn) {
        int r, g, b;
        int width = imageIn.getWidth();
        int height = imageIn.getHeight();
        int realxpos = (int)(width * Center.X);
        int realypos = (int)(height * Center.Y);
        float realradius = Math.min(width, height) * Radius;
        Image clone = imageIn.clone();
        for (int x = 0; x < width; x++)
        {
	        for (int y = 0; y < height; y++)
	        {
                float pos = 1f - ((float)Math.sqrt((double)(((x - realxpos) * (x - realxpos)) + (y - realypos) * (y - realypos))) / realradius);
                if (pos > 0f)
                {
                    pos = 1f - (Distortion * pos * pos);
                    float pos1 = (x - realxpos) * pos + realxpos;
                    float pos2 = (y - realypos) * pos + realypos;
                    int x1 = (int)pos1;
                    float pos3 = pos1 - x1;
                    int x2 = (pos3 > 0f) ? (x1 + 1) : x1;
                    int y1 = (int)pos2;
                    float pos4 = pos2 - y1;
                    int y2 = (pos4 > 0f) ? (y1 + 1) : y1;
                    if (x1 < 0){
                        x1 = 0;
                    }
                    else if (x1 >= width){
                        x1 = width - 1;
                    }
                    if (x2 < 0){
                        x2 = 0;
                    }
                    else if (x2 >= width){
                        x2 = width - 1;
                    }
                    if (y1 < 0){
                        y1 = 0;
                    }
                    else if (y1 >= height){
                        y1 = height - 1;
                    }
                    if (y2 < 0){
                        y2 = 0;
                    }
                    else if (y2 >= height){
                        y2 = height - 1;
                    }
                    r = clone.getRComponent(x1, y1);
                    g = clone.getGComponent(x1, y1);
                    b = clone.getBComponent(x1, y1);
                  
                    int r2 = clone.getRComponent(x2, y1);
                    int g2 = clone.getGComponent(x2, y1);
                    int b2 = clone.getBComponent(x2, y1);           
                    int r3 = clone.getRComponent(x2, y2);
                    int g3 = clone.getGComponent(x2, y2);
                    int b3 = clone.getBComponent(x2, y2);
                    int r4 = clone.getRComponent(x1, y2);
                    int g4 = clone.getGComponent(x1, y2);
                    int b4 = clone.getBComponent(x1, y2);
                    r = (int)((r * (1f - pos4) * (1f - pos3) + r2 * (1f - pos4) * pos3 + r3 * pos4 * pos3) + r4 * pos4 * (1f - pos3));
                    g = (int)((g * (1f - pos4) * (1f - pos3) + g2 * (1f - pos4) * pos3 + g3 * pos4 * pos3) + g4 * pos4 * (1f - pos3));
                    b = (int)((b * (1f - pos4) * (1f - pos3) + b2 * (1f - pos4) * pos3 + b3 * pos4 * pos3) + b4 * pos4 * (1f - pos3));
                }
                else {
                    r = clone.getRComponent(x, y);
                    g = clone.getGComponent(x, y);
                    b = clone.getBComponent(x, y);                   
                }
                imageIn.setPixelColor(x,y,r,g,b);
            }
        }
        return imageIn;
    }
}
