/* 
 * HaoRan ImageFilter Classes v0.3
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


public class SharpFilter implements IImageFilter{

	
	int _step;
	
	public SharpFilter() {
		_step = 1;
	}

	SharpFilter(int step){
		_step = step;
	}
	 
	public Image process(Image imageIn)
	{
		int height =imageIn.getHeight();
		int width =imageIn.getWidth();
		Image clone = imageIn.clone();
		imageIn.clearImage(0xffffff);
        
        //������˹ģ��
        int[] Laplacian = new int[]{ -1, -1, -1, -1, 8 +_step, -1, -1, -1, -1 };
        for (int x = 1; x < width - 1; x++)
		{
            for (int y = 1; y < height - 1; y++)
            {
                int r = 0, g = 0, b = 0;
                int Index = 0;
                for (int col = -1; col <= 1; col++)
				{
                    for (int row = -1; row <= 1; row++)
                    {
						int	rr = clone.getRComponent(x + row, y + col);
						int	gg = clone.getGComponent(x + row, y + col);
						int	bb = clone.getBComponent(x + row, y + col);

                        r += rr * Laplacian[Index];
                        g += gg * Laplacian[Index];
                        b += bb * Laplacian[Index];
                        Index++;
                    }
				}
				imageIn.setPixelColor(x-1, y-1, Image.SAFECOLOR(r), Image.SAFECOLOR(g), Image.SAFECOLOR(b));
            }
	    }
		return imageIn;
	}
}