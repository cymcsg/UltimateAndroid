/* 
 * HaoRan ImageFilter Classes v0.4
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

public class VideoFilter implements IImageFilter{
	public static class  VIDEO_TYPE { 
		public static int VIDEO_STAGGERED = 0;
		public static int VIDEO_TRIPED = 1;
		public static int VIDEO_3X3 = 2;
		public static int VIDEO_DOTS = 3;
	} 

    private int m_VideoType;

    private int[] pattern = null;

    public VideoFilter(int nVideoType)
    {
        this.m_VideoType = nVideoType;

        switch (m_VideoType)
        {
            case 0://VIDEO_TYPE.VIDEO_STAGGERED:
            {
                this.pattern = new int[]  
                {
                    0, 1,
                    0, 2,
                    1, 2,
                    1, 0,
                    2, 0,
                    2, 1,
                };
                break;
            }
            case 1://VIDEO_TYPE.VIDEO_TRIPED:
            {
                this.pattern = new int[]  
                {
                    0,
                    1,
                    2,
                };
                break;
            }
            case 2://VIDEO_TYPE.VIDEO_3X3:
            {
                this.pattern = new int[]  
                {
                    0, 1, 2,
                    2, 0, 1,
                    1, 2, 0,
                };
                break;
            }
            default:
            {
                this.pattern = new int[]  
                {
                    0, 1, 2, 0, 0,
                    1, 1, 1, 2, 0,
                    0, 1, 2, 2, 2,
                    0, 0, 1, 2, 0,
                    0, 1, 1, 1, 2,
                    2, 0, 1, 2, 2,
                    0, 0, 0, 1, 2,
                    2, 0, 1, 1, 1,
                    2, 2, 0, 1, 2,
                    2, 0, 0, 0, 1,
                    1, 2, 0, 1, 1,
                    2, 2, 2, 0, 1,
                    1, 2, 0, 0, 0,
                    1, 1, 2, 0, 1,
                    1, 2, 2, 2, 0,
                };
                break;
            }
        }
    }

    public Image process(Image imageIn)
    {
    	int[] pattern_width = new int[] { 2, 1, 3, 5 };
        int[] pattern_height = new int[] { 6, 3, 3, 15 };

        int r, g, b;
        for (int x = 0; x < imageIn.getWidth(); x++)
        {
            for (int y = 0; y < imageIn.getHeight(); y++)
            {
                r = imageIn.getRComponent(x, y);
                g = imageIn.getGComponent(x, y);
                b = imageIn.getBComponent(x, y);

                int nWidth = pattern_width[(int)m_VideoType];
                int nHeight = pattern_height[(int)m_VideoType];
                int index = nWidth * (y % nHeight) + (x % nWidth);

                index = pattern[index];
                if (index == 0)
                    r = Function.FClamp0255(2 * r);
                if (index == 1)
                    g = Function.FClamp0255(2 * g);
                if (index == 2)
                    b = Function.FClamp0255(2 * b);

                imageIn.setPixelColor(x, y, r, g, b);
            }
        }
        return imageIn;
    }
}
