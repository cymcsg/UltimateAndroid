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


public class ShiftFilter implements IImageFilter{

	int   _amount ; // max shift pixel
	
	/**
    Constructor 
    amount >= 2.
	*/
	public ShiftFilter(int amount)
	{
	    _amount = ((amount >= 2) ? amount : 2) ;
	}
  
    //@Override
    public Image process(Image imageIn) {
    	int r, g, b, m_current = 0;
		  int width = imageIn.getWidth();
		  int height = imageIn.getHeight();
		  Image clone = imageIn.clone();
		  for(int y = 0 ; y < height ; y++){
			  for(int x = 0 ; x < width ; x++){
				   if (x == 0) {
					   m_current = (NoiseFilter.getRandomInt(-255, 0xff) % _amount) * ((NoiseFilter.getRandomInt(-255, 0xff) % 2 > 0) ? 1 : -1) ;
				   }
				   int sx = (int)Function.FClamp(x+m_current, 0, width-1);
			       r = clone.getRComponent(sx, y);
				   g = clone.getGComponent(sx, y);
				   b = clone.getBComponent(sx, y);
				   imageIn.setPixelColor(x, y, r, g, b);
			  }
		  }
        return imageIn;     
    }
}