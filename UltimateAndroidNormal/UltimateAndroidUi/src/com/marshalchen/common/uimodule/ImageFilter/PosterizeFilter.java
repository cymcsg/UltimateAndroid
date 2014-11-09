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


public class PosterizeFilter extends LUTFilter{

	int   _level ;
	public int InitLUTtable (int LUTIndex)
    {
        double  d = 255.0 / (_level - 1.0) ;
        int     n = (int)(LUTIndex / d + 0.5) ; // round
        return Function.FClamp0255 (d * n) ; // round
    }
	 
	public PosterizeFilter (int nLevel)
    {
        _level = ((nLevel >= 2) ? nLevel : 2) ;
    }
}