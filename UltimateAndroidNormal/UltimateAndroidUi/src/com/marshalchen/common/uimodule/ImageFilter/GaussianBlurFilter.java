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

import java.lang.reflect.Array;

/**
 * ��˹ģ��filter
 * @author daizhj
 *
 */
public class GaussianBlurFilter  implements IImageFilter{
	protected static int Padding = 3;

    /// <summary>
    /// The bluriness factor. 
    /// Should be in the range [0, 40].
    /// </summary>
    public float Sigma = 0.75f;
    
    float[] ApplyBlur(float[] srcPixels, int width, int height)
    {
       float[] destPixels = new float[srcPixels.length];
       System.arraycopy(srcPixels, 0, destPixels, 0, srcPixels.length);

       int w = width + Padding*2;
       int h = height + Padding*2;

       // Calculate the coefficients
       float q = Sigma;
       float q2 = q * q;
       float q3 = q2 * q;

       float b0 = 1.57825f + 2.44413f * q + 1.4281f * q2 + 0.422205f * q3;
       float b1 = 2.44413f * q + 2.85619f * q2 + 1.26661f * q3;
       float b2 = -(1.4281f * q2 + 1.26661f * q3);
       float b3 = 0.422205f * q3;

       float b = 1.0f - ((b1 + b2 + b3) / b0);

       // Apply horizontal pass
       ApplyPass(destPixels, w, h, b0, b1, b2, b3, b);

       // Transpose the array
       float[] transposedPixels = new float[destPixels.length];
       Transpose(destPixels, transposedPixels, w, h);

       // Apply vertical pass
       ApplyPass(transposedPixels, h, w, b0, b1, b2, b3, b);

       // transpose back
       Transpose(transposedPixels, destPixels, h, w);

       return destPixels;
    }

    void ApplyPass(float[] pixels, int width, int height, float b0, float b1, float b2, float b3, float b)
    {
    	float num = 1f / b0;
	    int triplewidth = width * 3;
	    for (int i = 0; i < height; i++) {
	        int steplength = i * triplewidth;
	        for (int j = steplength + 9; j < (steplength + triplewidth); j += 3) {
	            pixels[j] = (b * pixels[j]) + ((((b1 * pixels[j - 3]) + (b2 * pixels[j - 6])) + (b3 * pixels[j - 9])) * num);
	            pixels[j + 1] = (b * pixels[j + 1]) + ((((b1 * pixels[(j + 1) - 3]) + (b2 * pixels[(j + 1) - 6])) + (b3 * pixels[(j + 1) - 9])) * num);
	            pixels[j + 2] = (b * pixels[j + 2]) + ((((b1 * pixels[(j + 2) - 3]) + (b2 * pixels[(j + 2) - 6])) + (b3 * pixels[(j + 2) - 9])) * num);
	        }
	        for (int k = ((steplength + triplewidth) - 9) - 3; k >= steplength; k -= 3){
	            pixels[k] = (b * pixels[k]) + ((((b1 * pixels[k + 3]) + (b2 * pixels[k + 6])) + (b3 * pixels[k + 9])) * num);
	            pixels[k + 1] = (b * pixels[k + 1]) + ((((b1 * pixels[(k + 1) + 3]) + (b2 * pixels[(k + 1) + 6])) + (b3 * pixels[(k + 1) + 9])) * num);
	            pixels[k + 2] = (b * pixels[k + 2]) + ((((b1 * pixels[(k + 2) + 3]) + (b2 * pixels[(k + 2) + 6])) + (b3 * pixels[(k + 2) + 9])) * num);
	        }
	    }
    }
  

    void Transpose(float[] input, float[] output, int width, int height)
    {
    	for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                int index = (j * height) * 3 + (i * 3);
                int pos = (i * width) * 3 + (j * 3);
                output[index] = input[pos];
                output[index + 1] = input[pos + 1];
                output[index + 2] = input[pos + 2];
            }
        }
    }
    

    float[] ConvertImageWithPadding(Image imageIn, int width, int height)
    {
    	int newheight = height + Padding*2;
        int newwidth = width + Padding*2;
        float[] numArray = new float[(newheight * newwidth) * 3];
        int index = 0;
        int num = 0;
        for (int i = -3; num < newheight; i++)
        {
            int y = i;
            if (i < 0){
                y = 0;
            }
            else if (i >= height){
                y = height - 1;
            }
            int count = 0;
            int negpadding = -1 * Padding;
            while (count < newwidth){
                int x = negpadding;
                if (negpadding < 0){
                    x = 0;
                }
                else if (negpadding >= width){
                    x = width - 1;
                }
                numArray[index] = imageIn.getRComponent(x, y) * 0.003921569f;
                numArray[index + 1] =imageIn.getGComponent(x, y) * 0.003921569f;
                numArray[index + 2] = imageIn.getBComponent(x, y) * 0.003921569f;
                
                count++; negpadding++;
                index += 3;
            }
            num++;
        }
        return numArray;
    }
    
    //@Override
    public Image process(Image imageIn) {
	    int width = imageIn.getWidth();
	    int height = imageIn.getHeight();
	    float[] imageArray = ConvertImageWithPadding(imageIn, width, height);
	    imageArray = ApplyBlur(imageArray, width, height);
	    int newwidth = width + Padding*2;
	    for (int i = 0; i < height; i++)
	    {
	        int num = ((i + 3) * newwidth) + 3;
	        for (int j = 0; j < width; j++){
	            int pos = (num + j) * 3;
	            imageIn.setPixelColor(j, i,  (byte) (imageArray[pos] * 255f),  (byte) (imageArray[pos + 1] * 255f), (byte) (imageArray[pos + 2] * 255f));
	        }
	    }
	    return imageIn;
    }

}
