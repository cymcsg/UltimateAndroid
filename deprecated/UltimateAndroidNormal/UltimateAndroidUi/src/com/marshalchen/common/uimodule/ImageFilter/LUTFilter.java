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

import com.marshalchen.common.uimodule.ImageFilter.IImageFilter.Function;


public class LUTFilter implements IImageFilter{

	
	protected int[]  m_LUT = new int[256] ;
	
	protected int InitLUTtable (int nLUTIndex){ 
		return nLUTIndex;
	}
	 
	public Image process(Image imageIn)
	{
		for (int i=0 ; i <= 0xFF ; i++)
			m_LUT[i] = InitLUTtable (i) ;
		int r, g, b;
		for(int x = 0 ; x < (imageIn.getWidth() - 1) ; x++){
			for(int y = 0 ; y < (imageIn.getHeight() - 1) ; y++){
				r = imageIn.getRComponent(x, y);
				g = imageIn.getGComponent(x, y);
				b = imageIn.getBComponent(x, y);
				
				imageIn.setPixelColor(x, y, Image.SAFECOLOR(m_LUT[r]), Image.SAFECOLOR(m_LUT[g]), Image.SAFECOLOR(m_LUT[b]));
			}
		}
		return imageIn;
	}
}