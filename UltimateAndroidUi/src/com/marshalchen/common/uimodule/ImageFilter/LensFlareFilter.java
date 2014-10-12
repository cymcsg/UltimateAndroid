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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.marshalchen.common.uimodule.ImageFilter.ImageBlender.BlendMode;
import com.marshalchen.common.uimodule.ImageFilter.RadialDistortionFilter.Point;
import android.graphics.Color;
import android.util.Log;


public class LensFlareFilter implements IImageFilter{

	class FloatRGB
    {
        double  r ;
        double  g ;
        double  b ;
    }
	
    class Reflect
    {
    	FloatRGB ccol = new FloatRGB() ;
    	double   size ;
    	int      xp ;
    	int      yp ;
    	int      type ;
    }

	Point      _point ;
    Reflect[]  _reflect = new Reflect[19];
    int        _numref ;
    double     _scolor, _sglow, _sinner, _souter, _shalo ;
    FloatRGB   _color = new FloatRGB() ;
    FloatRGB   _glow = new FloatRGB() ;
    FloatRGB   _inner = new FloatRGB();
    FloatRGB   _outer = new FloatRGB();
    FloatRGB   _halo = new FloatRGB() ;

     public LensFlareFilter(Point point){
    	 _point = point;
     }
     
     public LensFlareFilter(){
    	 _point = new Point(0.0f, 0.0f);
     }

     void initref (int sx, int sy, int width, int height, int matt)
     {
         int xh = width / 2;
         int yh = height / 2;
 		 int dx = xh - sx;
 		 int dy = yh - sy ;
         _numref = 19 ;
         _reflect[0] = new Reflect(); _reflect[0].type=1; _reflect[0].size=matt*0.027;
         _reflect[0].xp=(int)(0.6699*dx+xh); _reflect[0].yp=(int)(0.6699*dy+yh);
         _reflect[0].ccol.r=0.0; _reflect[0].ccol.g=14.0/255.0; _reflect[0].ccol.b=113.0/255.0;
         _reflect[1] = new Reflect(); _reflect[1].type=1; _reflect[1].size=matt*0.01;
         _reflect[1].xp=(int)(0.2692*dx+xh); _reflect[1].yp=(int)(0.2692*dy+yh);
         _reflect[1].ccol.r=90.0/255.0; _reflect[1].ccol.g=181.0/255.0; _reflect[1].ccol.b=142.0/255.0;
         _reflect[2] = new Reflect();_reflect[2].type=1; _reflect[2].size=matt*0.005;
         _reflect[2].xp=(int)(-0.0112*dx+xh); _reflect[2].yp=(int)(-0.0112*dy+yh);
         _reflect[2].ccol.r=56.0/255.0; _reflect[2].ccol.g=140.0/255.0; _reflect[2].ccol.b=106.0/255.0;
         _reflect[3] = new Reflect(); _reflect[3].type=2; _reflect[3].size=matt*0.031;
         _reflect[3].xp=(int)(0.6490*dx+xh); _reflect[3].yp=(int)(0.6490*dy+yh);
         _reflect[3].ccol.r=9.0/255.0; _reflect[3].ccol.g=29.0/255.0; _reflect[3].ccol.b=19.0/255.0;
         _reflect[4] = new Reflect();_reflect[4].type=2; _reflect[4].size=matt*0.015;
         _reflect[4].xp=(int)(0.4696*dx+xh); _reflect[4].yp=(int)(0.4696*dy+yh);
         _reflect[4].ccol.r=24.0/255.0; _reflect[4].ccol.g=14.0/255.0; _reflect[4].ccol.b=0.0;
         _reflect[5] = new Reflect();_reflect[5].type=2; _reflect[5].size=matt*0.037;
         _reflect[5].xp=(int)(0.4087*dx+xh); _reflect[5].yp=(int)(0.4087*dy+yh);
         _reflect[5].ccol.r=24.0/255.0; _reflect[5].ccol.g=14.0/255.0; _reflect[5].ccol.b=0.0;
         _reflect[6] = new Reflect();_reflect[6].type=2; _reflect[6].size=matt*0.022;
         _reflect[6].xp=(int)(-0.2003*dx+xh); _reflect[6].yp=(int)(-0.2003*dy+yh);
         _reflect[6].ccol.r=42.0/255.0; _reflect[6].ccol.g=19.0/255.0; _reflect[6].ccol.b=0.0;
         _reflect[7] = new Reflect();_reflect[7].type=2; _reflect[7].size=matt*0.025;
         _reflect[7].xp=(int)(-0.4103*dx+xh); _reflect[7].yp=(int)(-0.4103*dy+yh);
         _reflect[7].ccol.b=17.0/255.0; _reflect[7].ccol.g=9.0/255.0; _reflect[7].ccol.r=0.0;
         _reflect[8] = new Reflect();_reflect[8].type=2; _reflect[8].size=matt*0.058;
         _reflect[8].xp=(int)(-0.4503*dx+xh); _reflect[8].yp=(int)(-0.4503*dy+yh);
         _reflect[8].ccol.b=10.0/255.0; _reflect[8].ccol.g=4.0/255.0; _reflect[8].ccol.r=0.0;
         _reflect[9] = new Reflect(); _reflect[9].type=2; _reflect[9].size=matt*0.017;
         _reflect[9].xp=(int)(-0.5112*dx+xh); _reflect[9].yp=(int)(-0.5112*dy+yh);
         _reflect[9].ccol.r=5.0/255.0; _reflect[9].ccol.g=5.0/255.0; _reflect[9].ccol.b=14.0/255.0;
         _reflect[10] = new Reflect();_reflect[10].type=2; _reflect[10].size=matt*0.2;
         _reflect[10].xp=(int)(-1.496*dx+xh); _reflect[10].yp=(int)(-1.496*dy+yh);
         _reflect[10].ccol.r=9.0/255.0; _reflect[10].ccol.g=4.0/255.0; _reflect[10].ccol.b=0.0;
         _reflect[11] = new Reflect();_reflect[11].type=2; _reflect[11].size=matt*0.5;
         _reflect[11].xp=(int)(-1.496*dx+xh); _reflect[11].yp=(int)(-1.496*dy+yh);
         _reflect[11].ccol.r=9.0/255.0; _reflect[11].ccol.g=4.0/255.0; _reflect[11].ccol.b=0.0;
         _reflect[12] = new Reflect(); _reflect[12].type=3; _reflect[12].size=matt*0.075;
         _reflect[12].xp=(int)(0.4487*dx+xh); _reflect[12].yp=(int)(0.4487*dy+yh);
         _reflect[12].ccol.r=34.0/255.0; _reflect[12].ccol.g=19.0/255.0; _reflect[12].ccol.b=0.0;
         _reflect[13] = new Reflect();_reflect[13].type=3; _reflect[13].size=matt*0.1;
         _reflect[13].xp=(int)(dx+xh); _reflect[13].yp=(int)(dy+yh);
         _reflect[13].ccol.r=14.0/255.0; _reflect[13].ccol.g=26.0/255.0; _reflect[13].ccol.b=0.0;
         _reflect[14] = new Reflect();_reflect[14].type=3; _reflect[14].size=matt*0.039;
         _reflect[14].xp=(int)(-1.301*dx+xh); _reflect[14].yp=(int)(-1.301*dy+yh);
         _reflect[14].ccol.r=10.0/255.0; _reflect[14].ccol.g=25.0/255.0; _reflect[14].ccol.b=13.0/255.0;
         _reflect[15] = new Reflect(); _reflect[15].type=4; _reflect[15].size=matt*0.19;
         _reflect[15].xp=(int)(1.309*dx+xh); _reflect[15].yp=(int)(1.309*dy+yh);
         _reflect[15].ccol.r=9.0/255.0; _reflect[15].ccol.g=0.0; _reflect[15].ccol.b=17.0/255.0;
         _reflect[16] = new Reflect();_reflect[16].type=4; _reflect[16].size=matt*0.195;
         _reflect[16].xp=(int)(1.309*dx+xh); _reflect[16].yp=(int)(1.309*dy+yh);
         _reflect[16].ccol.r=9.0/255.0; _reflect[16].ccol.g=16.0/255.0; _reflect[16].ccol.b=5.0/255.0;
         _reflect[17] = new Reflect();_reflect[17].type=4; _reflect[17].size=matt*0.20;
         _reflect[17].xp=(int)(1.309*dx+xh); _reflect[17].yp=(int)(1.309*dy+yh);
         _reflect[17].ccol.r=17.0/255.0; _reflect[17].ccol.g=4.0/255.0; _reflect[17].ccol.b=0.0;
         _reflect[18] = new Reflect();_reflect[18].type=4; _reflect[18].size=matt*0.038;
         _reflect[18].xp=(int)(-1.301*dx+xh); _reflect[18].yp=(int)(-1.301*dy+yh);
         _reflect[18].ccol.r=17.0/255.0; _reflect[18].ccol.g=4.0/255.0; _reflect[18].ccol.b=0.0;
     }

     static int fixpix (int color, double procent, final FloatRGB colpro)
     {
    	int colorR = (color & 0xFF0000)>>16;
    	int colorG = (color & 0x00FF00)>>8;
    	int colorB = color & 0x0000FF;
 		colorR = colorR +(int)((255 - colorR) * procent * colpro.r);
 		colorG = colorG +(int)((255 - colorG) * procent * colpro.g);
 		colorB = colorB +(int)((255 - colorB) * procent * colpro.b);
 		return Color.rgb(colorR, colorG, colorB);
     }

     int mcolor (int color, double h)
     {
         double   procent = 1 - h/_scolor ;
         if (procent > 0)
         {
             procent *= procent ;
             color = fixpix(color, procent, _color) ;
         }
 		return color;
     }

     int mglow (int color, double h)
     {
         double   procent = 1 - h/_sglow ;
         if (procent > 0)
         {
             procent *= procent ;
             color = fixpix(color, procent, _glow) ;
         }
 		return color;
     }

     int minner (int color, double h)
     {
         double   procent = 1 - h/_sinner ;
         if (procent > 0)
         {
             procent *= procent ;
             color = fixpix(color, procent, _inner) ;
         }
 		return color;
     }

     int mouter (int color, double h)
     {
         double   procent = 1 - h/_souter ;
         if (procent > 0)
             color = fixpix (color, procent, _outer) ;
 		return color;
     }

     int mhalo (int color,  double h)
     {
         double   procent = Math.abs((h - _shalo) / (_shalo * 0.07)) ;
         if (procent < 1)
             color = fixpix (color, 1 - procent, _halo) ;
 		return color;
     }

     int mrt1 (int color,  Reflect r, int x, int y)
     {
         double   procent = 1 - FHypot (r.xp - x, r.yp - y) / r.size ;
         if (procent > 0)
         {
             procent *= procent ;
             color = fixpix (color, procent, r.ccol) ;
         }
 		return color;
     }

     int mrt2 (int color, Reflect r, int x, int y)
     {
         double   procent = r.size - FHypot (r.xp - x, r.yp - y) ;
         procent /= (r.size * 0.15) ;
         if (procent > 0)
         {
             if (procent > 1)
                 procent = 1 ;
             color = fixpix (color, procent, r.ccol) ;
         }
 		return color;
     }

     int mrt3 (int color, Reflect r, int x, int y)
     {
         double   procent = r.size - FHypot (r.xp - x, r.yp - y) ;
         procent /= (r.size * 0.12) ;
         if (procent > 0)
         {
             if (procent > 1)
                 procent = 1 - (procent * 0.12) ;
             color = fixpix (color, procent, r.ccol) ;
         }
 		return color;
     }

     int mrt4 (int color, Reflect r, int x, int y)
     {
         double   procent  = FHypot (r.xp - x, r.yp - y) - r.size ;
         procent /= (r.size * 0.04) ;
         procent  = Math.abs (procent) ;
         if (procent < 1)
             color = fixpix (color, 1 - procent, r.ccol) ;
 		return color;
     }
     
     public Image process(Image imageIn) 
     {
    	int matt = imageIn.getWidth();
  		int width = matt;
  		int height = imageIn.getHeight();
    	if((_point.X + _point.Y) <= 0){
 			_point.X = width /2;
 			_point.Y = height /2;
 		}
         _scolor = matt * 0.0375;
         _sglow  = matt * 0.078125;
         _sinner = matt * 0.1796875;
         _souter = matt * 0.3359375;
         _shalo  = matt * 0.084375;

         _color.r = 239.0/255.0; _color.g = 239.0/255.0; _color.b = 239.0/255.0;
         _glow.r  = 245.0/255.0; _glow.g  = 245.0/255.0; _glow.b  = 245.0/255.0;
         _inner.r = 255.0/255.0; _inner.g = 38.0/255.0;  _inner.b = 43.0/255.0;
         _outer.r = 69.0/255.0;  _outer.g = 59.0/255.0;  _outer.b = 64.0/255.0;
         _halo.r  = 80.0/255.0;  _halo.g  = 15.0/255.0;  _halo.b  = 4.0/255.0;

         initref ((int)_point.X, (int)_point.Y, width, imageIn.getHeight(), matt) ;
 		 for(int x = 0 ; x < width; x++){
 			  for(int y = 0 ; y < height ; y++){
 				    double   hyp = FHypot (x-_point.X, y-_point.Y) ;
 					int color = imageIn.getPixelColor(x, y);
 					color = mcolor (color, hyp); /* make color */
 					color = mglow (color, hyp);  /* make _glow  */
 					color = minner (color, hyp); /* make _inner */
 					color = mouter (color, hyp); /* make _outer */
 					color = mhalo (color, hyp);  /* make _halo  */

 					for (int i=0 ; i < _numref ; i++)
 					{
 						switch (_reflect[i].type)
 						{
 							case 1 : {color = mrt1 (color, _reflect[i], x, y); break;}
 							case 2 : {color = mrt2 (color, _reflect[i], x, y); break;}
 							case 3 : {color = mrt3 (color, _reflect[i], x, y); break;}
 							case 4 : {color = mrt4 (color, _reflect[i], x, y); break;}
 						}
 					}
 					imageIn.setPixelColor(x, y, color);
 			  }
 		 }
 		 return imageIn;
     }
     
     static double FHypot (double x, double y)
     {
         return Math.sqrt (x*x + y*y) ;
     }
}
