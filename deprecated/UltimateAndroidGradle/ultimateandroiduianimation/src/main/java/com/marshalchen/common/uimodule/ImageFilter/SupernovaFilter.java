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

import com.marshalchen.common.uimodule.ImageFilter.RadialDistortionFilter.Point;


public class SupernovaFilter implements IImageFilter{
	
	Point    _pt ;
    double   _radius ;
    int      _count ;
    static int      RAND_MAX = 0x7fff;
    double[]  _spoke ;
    int[]  _spokecolor ;
    
    public SupernovaFilter(int cr, int radius, int count){
    	 this(new Point(0.0f, 0.0f), cr, radius, count);
    }
   
    public SupernovaFilter(Point pt, int cr, int radius, int count)
    {
        _pt = pt ;
        _radius = BoundParam1(radius) ;
        _count = BoundParam1(count) ;
        _spoke = new double[BoundParam1(count)];
        _spokecolor = new int[BoundParam1(count)];
        for (int i=0 ; i < _count ; i++)
        {
            _spoke[i] = get_gauss() ;
            _spokecolor[i] = cr ;
        }
    }
    
    static double get_gauss()
    {
        double   s = 0 ;
        for (int i=0 ; i < 6 ; i++)
            s = s + NoiseFilter.getRandomInt(-255, 255) / (double)(RAND_MAX + 1.0) ;
        return s / 6.0 ;
    }

    static int BoundParam1 (int n)
    {
        return ((n >= 1) ? n : 1) ;
    }
    
   

	
    
    //@Override
    public Image process(Image imageIn) {
    	if((_pt.X + _pt.Y) <= 0){
			_pt.X = imageIn.getWidth() /2;
			_pt.Y = imageIn.getHeight() /2;
		}
		for(int x = 0 ; x < (imageIn.getWidth() - 1) ; x++){
			  for(int y = 0 ; y < (imageIn.getHeight() - 1) ; y++){
				int[] pixel = new int[3];
				pixel[0] =  imageIn.getRComponent(x, y);
				pixel[1] =  imageIn.getGComponent(x, y);
				pixel[2] =  imageIn.getBComponent(x, y);
				
				double   u = (x - _pt.X + 0.001) / _radius ;
				double   v = (y - _pt.Y + 0.001) / _radius ;
	
				double   t = (Math.atan2 (u, v) / (2 * LIB_PI) + 0.51) * _count ;
				int      i = (int)Math.floor(t) ;
				t -= i ;
				i %= _count ;
	
				double   w1 = _spoke[i] * (1-t) + _spoke[(i+1) % _count] * t ;
				w1 = w1 * w1 ;
	
				double   w = 1.0 / Math.sqrt (u*u + v*v) * 0.9 ;
				double   fRatio = Function.FClampDouble(w, 0.0, 1.0) ;
	
				double   ws = Function.FClampDouble (w1 * w, 0.0, 1.0) ;
	
				for (int m=0 ; m < 3 ; m++)
				{
					int[] _spokecolorm = new int[3];
					_spokecolorm[0] = _spokecolor[i] &0xFF0000>>16;
					_spokecolorm[1] = _spokecolor[i] &0x00FF00>>8;
					_spokecolorm[2] = _spokecolor[i] &0x0000FF;
					
					int[] _spokecolorcount = new int[3];
					_spokecolorcount[0] = _spokecolor[(i+1) % _count] &0xFF0000>>16;
			        _spokecolorcount[1] = _spokecolor[(i+1) % _count] &0x00FF00>>8;
			        _spokecolorcount[2] = _spokecolor[(i+1) % _count] &0x0000FF;
					
					double   spokecol = _spokecolorm[m]/255.0 * (1-t) +_spokecolorcount[m]/255.0 * t ;
	
					double   r;
					if (w > 1.0)
						r = Function.FClampDouble (spokecol * w, 0.0, 1.0);
					else {
						r = pixel[m]/255.0 * (1.0 - fRatio) + spokecol * fRatio ;
					}
	
					r += ws ;
					pixel[m] = Function.FClamp0255 (r*255) ;
					imageIn.setPixelColor(x, y, pixel[0], pixel[1], pixel[2]);
				}
			}
		}
        return imageIn;
    }

}
