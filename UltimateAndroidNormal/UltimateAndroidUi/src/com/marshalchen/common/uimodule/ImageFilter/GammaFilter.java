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


public class GammaFilter extends LUTFilter{

	
	double   _fInvGamma ;
	public int InitLUTtable (int nLUTIndex)
    {
        double   fMax = Math.pow (255.0, _fInvGamma) / 255.0 ;
        double   d = Math.pow((double)nLUTIndex, _fInvGamma) ;
        return Function.FClamp0255(d / fMax) ;
    }
	 
	public GammaFilter (int gamma)
    {
        gamma = ((gamma >= 1) ? gamma : 1) ;
        _fInvGamma = 1.0 / (gamma / 100.0) ;
    }
}