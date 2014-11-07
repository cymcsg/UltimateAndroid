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

public class MirrorFilter implements IImageFilter{
	/**
     * ���ӷ���
     */
   private boolean IsHorizontal = true;

   public MirrorFilter(boolean IsHorizontal)
   {
       this.IsHorizontal = IsHorizontal;
   }

   public Image process(Image imageIn)
   {
       int height = imageIn.getHeight();
       int width = imageIn.getWidth();
       int color, color2;

       if (IsHorizontal)
       {
           for (int y = 0; y < height; y++)
           {
               for (int x = 0; x < (width / 2); x++)
               {
                   color = imageIn.getPixelColor(x, y);
                   color2 = imageIn.getPixelColor(width - 1 - x, y);
                   imageIn.setPixelColor(width - 1 - x, y, color);
                   imageIn.setPixelColor(x, y, color2);
               }
           }
       }
       else
       {
           for (int x = 0; x < width; x++)
           {
               for (int y = 0; y < (height / 2); y++)
               {
                   color = imageIn.getPixelColor(x, y);
                   color2 = imageIn.getPixelColor(x, height - 1 - y);
                   imageIn.setPixelColor(x, height - 1 - y, color);
                   imageIn.setPixelColor(x, y, color2);
               }
           }
       }
       return imageIn;
   }

}
