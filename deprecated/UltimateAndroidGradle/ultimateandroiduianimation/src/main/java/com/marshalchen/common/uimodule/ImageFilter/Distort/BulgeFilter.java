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

package com.marshalchen.common.uimodule.ImageFilter.Distort;


import com.marshalchen.common.uimodule.ImageFilter.IImageFilter;

public class BulgeFilter extends BilinearDistort{
	double   _amount ;
    double   _offsetX ;
    double   _offsetY ;
	 
    public BulgeFilter (int amount)
    {
        this(amount, 0, 0);
    }
    
    public BulgeFilter (int amount, double offsetX, double offsetY)
    {
        _amount = amount / 100.0 ;
        _offsetX = IImageFilter.Function.FClampDouble(offsetX, -1.0, 1.0) ;
        _offsetY = IImageFilter.Function.FClampDouble(offsetY, -1.0, 1.0) ;
    }
    
    public double[] calc_undistorted_coord (int x, int y, double un_x, double un_y)
    {
        double hw = clone.getWidth() / 2.0 ;
        double hh = clone.getHeight() / 2.0 ;
        double maxrad = (hw < hh ? hw : hh) ;
        hw += _offsetX * hw ;
        hh += _offsetY * hh ;

        double u = x - hw ;
        double v = y - hh ;
        double r = Math.sqrt(u*u + v*v) ;
        double rscale1 = 1.0 - (r / maxrad) ;
        if (rscale1 > 0)
        {
            double rscale2 = 1.0 - _amount * rscale1 * rscale1 ;
            un_x = IImageFilter.Function.FClampDouble(u * rscale2 + hw, 0.0, clone.getWidth() - 1.0) ;
            un_y = IImageFilter.Function.FClampDouble(v * rscale2 + hh, 0.0, clone.getHeight() - 1.0) ;
        }
        else
        {
            un_x = x ;
            un_y = y ;
        }
        return new double[]{un_x, un_y};
    }
    

}
