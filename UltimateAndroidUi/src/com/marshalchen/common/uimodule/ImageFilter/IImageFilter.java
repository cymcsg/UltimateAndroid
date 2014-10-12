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

public interface IImageFilter {
	 public Image process(Image imageIn);
	 
	 public static double  LIB_PI = 3.14159265358979323846;
	 
	public static class Function{
		//-------------------------------------------------------------------------------------
		// basic function
		//-------------------------------------------------------------------------------------
		// bound in [tLow, tHigh]
		public static int FClamp(final int t, final int tLow, final int tHigh)
		{
			if (t < tHigh)
			{
				return ((t > tLow) ? t : tLow) ;
			}
			return tHigh ;
		}
		
		public static double FClampDouble(final double t, final double tLow, final double tHigh)
		{
			if (t < tHigh)
			{
				return ((t > tLow) ? t : tLow) ;
			}
			return tHigh ;
		}
		
		public static int FClamp0255(final double d)
		{
			return (int)(FClampDouble(d, 0.0, 255.0) + 0.5) ;
		}
	}
}
