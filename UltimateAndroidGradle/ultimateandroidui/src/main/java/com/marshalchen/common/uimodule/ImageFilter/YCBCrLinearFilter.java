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


public class YCBCrLinearFilter implements IImageFilter{
	static class MyColor{
		public int R;
		public int G;
		public int B;
		public MyColor(){}
	
		public MyColor(int r, int g, int b){
			 R = r;
			 G = g;
			 B = b;
		}
	
		public MyColor(int rgb){
					R = ((rgb& 0x00FF0000) >> 16);
				    G = ((rgb& 0x0000FF00) >> 8);
					B = ( rgb& 0x000000FF);
				}
	
		static int rgb(int r, int g, int b){
			return (255 << 24) + (r << 16) + (g << 8) + b;
		}
	}

	
    private Range inY = new Range(0.0f, 1.0f);
    private Range inCb = new Range(-0.5f, 0.5f);
    private Range inCr = new Range(-0.5f, 0.5f);

    private Range outY = new Range(0.0f, 1.0f);
    private Range outCb = new Range(-0.5f, 0.5f);
    private Range outCr = new Range(-0.5f, 0.5f);

    public YCBCrLinearFilter(Range inCb)
    {
        this.inCb = inCb;
    }
   
    public YCBCrLinearFilter(Range inCb, Range inCr)
    {
        this.inCb = inCb;
        this.inCr = inCr;
    }


    public Image process(Image imageIn)
    {
    	MyColor rgb = new MyColor();
        YCbCr ycbcr = new YCbCr();

        float ky = 0, by = 0;
        float kcb = 0, bcb = 0;
        float kcr = 0, bcr = 0;

        // Y line parameters
        if (inY.Max != inY.Min)
        {
            ky = (outY.Max - outY.Min) / (inY.Max - inY.Min);
            by = outY.Min - ky * inY.Min;
        }
        // Cb line parameters
        if (inCb.Max != inCb.Min)
        {
            kcb = (outCb.Max - outCb.Min) / (inCb.Max - inCb.Min);
            bcb = outCb.Min - kcb * inCb.Min;
        }
        // Cr line parameters
        if (inCr.Max != inCr.Min)
        {
            kcr = (outCr.Max - outCr.Min) / (inCr.Max - inCr.Min);
            bcr = outCr.Min - kcr * inCr.Min;
        }

        for (int x = 0; x < imageIn.getWidth(); x++)
        {
            for (int y = 0; y < imageIn.getHeight(); y++)
            {
                rgb.R = imageIn.getRComponent(x, y);
                rgb.G = imageIn.getGComponent(x, y);
                rgb.B = imageIn.getBComponent(x, y);

                // convert to YCbCr
                ycbcr = YCbCr.FromRGB(rgb, ycbcr);

                // correct Y
                if (ycbcr.Y >= inY.Max)
                    ycbcr.Y = outY.Max;
                else if (ycbcr.Y <= inY.Min)
                    ycbcr.Y = outY.Min;
                else
                    ycbcr.Y = ky * ycbcr.Y + by;

                // correct Cb
                if (ycbcr.Cb >= inCb.Max)
                    ycbcr.Cb = outCb.Max;
                else if (ycbcr.Cb <= inCb.Min)
                    ycbcr.Cb = outCb.Min;
                else
                    ycbcr.Cb = kcb * ycbcr.Cb + bcb;

                // correct Cr
                if (ycbcr.Cr >= inCr.Max)
                    ycbcr.Cr = outCr.Max;
                else if (ycbcr.Cr <= inCr.Min)
                    ycbcr.Cr = outCr.Min;
                else
                    ycbcr.Cr = kcr * ycbcr.Cr + bcr;

                // convert back to RGB
                rgb = YCbCr.ToRGB(ycbcr, rgb);

                imageIn.setPixelColor(x, y, rgb.R, rgb.G, rgb.B);
            }
        }
        return imageIn;
    }


    /// <summary>
    /// YCbCr components.
    /// </summary>
    /// 
    /// <remarks>The class encapsulates <b>YCbCr</b> color components.</remarks>
    /// 
    public static class YCbCr
    {
        /// <summary>
        /// Index of <b>Y</b> component.
        /// </summary>
        public short YIndex = 0;

        /// <summary>
        /// Index of <b>Cb</b> component.
        /// </summary>
        public short CbIndex = 1;

        /// <summary>
        /// Index of <b>Cr</b> component.
        /// </summary>
        public short CrIndex = 2;

        /// <summary>
        /// <b>Y</b> component.
        /// </summary>
        public float Y;

        /// <summary>
        /// <b>Cb</b> component.
        /// </summary>
        public float Cb;

        /// <summary>
        /// <b>Cr</b> component.
        /// </summary>
        public float Cr;

        /// <summary>
        /// Initializes a new instance of the <see cref="YCbCr"/> class.
        /// </summary>
        public YCbCr() { }

        /// <summary>
        /// Initializes a new instance of the <see cref="YCbCr"/> class.
        /// </summary>
        /// 
        /// <param name="y"><b>Y</b> component.</param>
        /// <param name="cb"><b>Cb</b> component.</param>
        /// <param name="cr"><b>Cr</b> component.</param>
        /// 
        public YCbCr(float y, float cb, float cr)
        {
            this.Y = Math.max(0.0f, Math.min(1.0f, y));
            this.Cb = Math.max(-0.5f, Math.min(0.5f, cb));
            this.Cr = Math.max(-0.5f, Math.min(0.5f, cr));
        }

        /// <summary>
        /// Convert from RGB to YCbCr color space (Rec 601-1 specification). 
        /// </summary>
        /// 
        /// <param name="rgb">Source color in <b>RGB</b> color space.</param>
        /// <param name="ycbcr">Destination color in <b>YCbCr</b> color space.</param>
        /// 
        public static YCbCr FromRGB(MyColor rgb, YCbCr ycbcr)
        {
            float r = (float)rgb.R / 255;
            float g = (float)rgb.G / 255;
            float b = (float)rgb.B / 255;

            ycbcr.Y = (float)(0.2989 * r + 0.5866 * g + 0.1145 * b);
            ycbcr.Cb = (float)(-0.1687 * r - 0.3313 * g + 0.5000 * b);
            ycbcr.Cr = (float)(0.5000 * r - 0.4184 * g - 0.0816 * b);
            return ycbcr;
        }

        /// <summary>
        /// Convert from RGB to YCbCr color space (Rec 601-1 specification).
        /// </summary>
        /// 
        /// <param name="rgb">Source color in <b>RGB</b> color space.</param>
        /// 
        /// <returns>Returns <see cref="YCbCr"/> instance, which represents converted color value.</returns>
        /// 
        public static YCbCr FromRGB(MyColor rgb)
        {
            YCbCr ycbcr = new YCbCr();
            FromRGB(rgb, ycbcr);
            return ycbcr;
        }

        /// <summary>
        /// Convert from YCbCr to RGB color space.
        /// </summary>
        /// 
        /// <param name="ycbcr">Source color in <b>YCbCr</b> color space.</param>
        /// <param name="rgb">Destination color in <b>RGB</b> color spacs.</param>
        /// 
        public static MyColor ToRGB(YCbCr ycbcr, MyColor rgb)
        {
            // don't warry about zeros. compiler will remove them
            float r = Math.max(0.0f, Math.min(1.0f, (float)(ycbcr.Y + 0.0000 * ycbcr.Cb + 1.4022 * ycbcr.Cr)));
            float g = Math.max(0.0f, Math.min(1.0f, (float)(ycbcr.Y - 0.3456 * ycbcr.Cb - 0.7145 * ycbcr.Cr)));
            float b = Math.max(0.0f, Math.min(1.0f, (float)(ycbcr.Y + 1.7710 * ycbcr.Cb + 0.0000 * ycbcr.Cr)));

            rgb.R = (byte)(r * 255);
            rgb.G = (byte)(g * 255);
            rgb.B = (byte)(b * 255);
            //rgb.Alpha = 255;
            return rgb;
        }

        /// <summary>
        /// Convert the color to <b>RGB</b> color space.
        /// </summary>
        /// 
        /// <returns>Returns <see cref="RGB"/> instance, which represents converted color value.</returns>
        /// 
        public MyColor ToRGB()
        {
        	MyColor rgb = new MyColor();
            ToRGB(this, rgb);
            return rgb;
        }
    }

    public static class Range
    {
        public float Min, Max;

      
        /// <summary>
        /// Length of the range (deffirence between maximum and minimum values).
        /// </summary>
        public float Length()
        {
            return Max - Min; 
        }


        /// <summary>
        /// Initializes a new instance of the <see cref="Range"/> structure.
        /// </summary>
        /// 
        /// <param name="min">Minimum value of the range.</param>
        /// <param name="max">Maximum value of the range.</param>
        /// 
        public Range(float min, float max)
        {
            this.Min = min;
            this.Max = max;
        }
    }
}
