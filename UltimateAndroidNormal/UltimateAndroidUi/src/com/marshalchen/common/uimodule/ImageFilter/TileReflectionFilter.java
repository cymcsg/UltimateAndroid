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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Point;
import android.util.Log;


public class TileReflectionFilter implements IImageFilter{
	
    // angle ==> radian
    double AngleToRadian (int nAngle) {return IImageFilter.LIB_PI * nAngle / 180.0;}

	double   m_sin, m_cos ;
    double   m_scale ;
    double   m_curvature ;
	final   int aasamples = 17;
	Point[]   m_aapt = new Point[aasamples];
	byte m_focusType;
    int m_size = 24;
      
    /**
	Constructor \n
	param -45 <= nAngle <= 45 \n
	param 2 <= nSquareSize <= 200 \n
	param -20 <= nCurvature <= 20
	*/
	public TileReflectionFilter (int nSquareSize, int nCurvature, int nAngle, byte focusType)
	{
	    nAngle = Function.FClamp (nAngle, -45, 45) ;
	    m_sin = Math.sin (AngleToRadian(nAngle)) ;
	    m_cos = Math.cos (AngleToRadian(nAngle)) ;
	
	    nSquareSize = Function.FClamp (nSquareSize, 2, 200) ;
	    m_scale = IImageFilter.LIB_PI / nSquareSize ;
	    m_focusType = focusType;
	    nCurvature = Function.FClamp (nCurvature, -20, 20) ;
	    if (nCurvature == 0)
	        nCurvature = 1 ;
	    m_curvature = nCurvature * nCurvature / 10.0 * (Math.abs(nCurvature)/nCurvature) ;
	
	    for (int i=0 ; i < aasamples ; i++)
	    {
	        double  x = (i * 4) / (double)aasamples,
	                y = i / (double)aasamples ;
	        x = x - (int)x ;
	        m_aapt[i] = new Point((int)(m_cos * x + m_sin * y), (int)(m_cos * y - m_sin * x));
	    }
	}
	
	
    public TileReflectionFilter(int nSquareSize, int nCurvature)
    {
    	 this(nSquareSize, nCurvature, 45, (byte)0);
    }

    
	
	
	public Image process(Image imageIn) {
	{
		  int r, g, b;
		  int width = imageIn.getWidth();
		  int height = imageIn.getHeight();
		  double hw = width / 2.0;
          double hh = imageIn.getHeight() / 2.0;
          
          int ratio = width > height ? height * 32768 / width : width * 32768 / height;

          // Calculate center, min and max
          int cx = width >> 1;
          int cy = height >> 1;
          int max = cx * cx + cy * cy;
          int min = (int)(max * 0.5);
          int diff = max - min;
          
		  for(int x = 0 ; x < width ; x++){
			  for(int y = 0 ; y < height ; y++){
				  if (m_focusType == 1)//��Բ
                  {
                      // Calculate distance to center and adapt aspect ratio
                      int dx = cx - x;
                      int dy = cy - y;
                      if (imageIn.getWidth() > imageIn.getHeight())
                      {
                          dy = (dy * ratio) >> 14;
                      }
                      else
                      {
                          dx = (dx * ratio) >> 14;
                      }
                      int distSq = dx * dx + dy * dy;

                      if (distSq <= min)
                          continue;
                  }
                  else if (m_focusType == 2)//������
                  {
                      boolean inarray = false;
                      if ((x < m_size) && (y < height - x) && (y >= x))
                          inarray = true; // left
                      else if ((y < m_size) && (x < width - y) && (x >= y))
                          inarray = true; // top
                      else if ((x > width - m_size) && (y >= width - x) && (y < height + x - width))
                          inarray = true; // right
                      else if (y > height - m_size)
                          inarray = true; // bottom
                      if (!inarray)
                          continue;
                  }
					int i = (int)(x - hw);
					int j = (int)(y - hh);
					b=0; g=0; r=0;
					for (int mm=0 ; mm < aasamples ; mm++){
						double   u = i + m_aapt[mm].x ;
						double   v = j - m_aapt[mm].y ;
					
						double   s =  m_cos * u + m_sin * v ;
						double   t = -m_sin * u + m_cos * v ;
					
						s += m_curvature * Math.tan(s * m_scale) ;
						t += m_curvature * Math.tan(t * m_scale) ;
						u = m_cos * s - m_sin * t ;
						v = m_sin * s + m_cos * t ;
					
						int xSample = (int)(hw + u) ;
						int ySample = (int)(hh + v) ;
					
						xSample = Function.FClamp (xSample, 0, width -1) ;
						ySample = Function.FClamp (ySample, 0, height-1) ;
					
						r += imageIn.getRComponent(xSample, ySample);
					    g += imageIn.getGComponent(xSample, ySample);
					    b += imageIn.getBComponent(xSample, ySample);
					 }
				 imageIn.setPixelColor(x, y, Image.SAFECOLOR(r/aasamples), Image.SAFECOLOR(g/aasamples), Image.SAFECOLOR(b/aasamples));
			  }
		  }
		  return imageIn;
	 }
   }
}
