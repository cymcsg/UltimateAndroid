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

/**
 * �Զ�У��Ч��
 * @author daizhj
 *
 */
public class AutoLevelFilter implements IImageFilter{
	public float Intensity = 1f;
	  
	private static float[] ComputeGamma(int[] lo,  int[] md,  int[] hi)
	{
	    float[] array = new float[3];
	    for (int i = 0; i < 3; i++){
	        if (lo[i] < md[i] && md[i] < hi[i]) {
	            double log = Math.log1p(/*0.5, */(double) (((float) (md[i] - lo[i])) / ((float) (hi[i] - lo[i]))));
	            array[i] = (log > 10.0) ? ((float) 10.0) : ((log < 0.1) ? ((float) 0.1) : ((float) log));
	        }
	        else{
	            array[i] = 1f;
	        }
	    }
	    return array;
	}

	public int[] GetMeanColor(int[][] h)
	{
	    float[] array = new float[3];
	    for (int i = 0; i < 3; i++) {
	        long sum1 = 0L;
	        long sum2 = 0L;
	        for (int j = 0; j < 256; j++){
	            sum1 += j * h[i][j];
	            sum2 += h[i][j];
	        }
	        array[i] = (sum2 == 0L) ? 0f : (((float) sum1) / ((float) sum2));
	    }
	    return new int[] { (((int) (array[0] + 0.5f)) & 255), (((int) (array[1] + 0.5f)) & 255), (((int) (array[2] + 0.5f)) & 255) };
	}

	public int[] GetPercentileColor(int[][] h, float fraction)
	{
	    int[] array = new int[3];
	    for (int i = 0; i < 3; i++)
	    {
	        long sum1 = 0L;
	        long sum2 = 0L;
	        for (int j = 0; j < 256; j++) {
	            sum2 += h[i][j];
	        }
	        for (int k = 0; k < 256; k++) {
	            sum1 += h[i][k];
	            if (sum1 > (sum2 * fraction)) {
	                array[i] = k;
	                break;
	            }
	        }
	    }
	    return array;
	}


	  
    //@Override
    public Image process(Image imageIn) {
    	int[][] h = new int[3][256];
	    int[] array = new int[3];
	    int[] rgb = new int[] { 255, 255, 255 };
	    int[] bb = new int[256];
	    int[] gg = new int[256];
	    int[] rr = new int[256];
	    int intensity = (int) (this.Intensity * 255f);
	    int intensity_invert = 255 - intensity;
	    for (int x = 0; x < imageIn.getWidth() - 1; x++){ 
			 for (int y = 0; y < imageIn.getHeight() - 1; y++)  {
	   			  h[0][imageIn.getRComponent(x, y)]++;
	  	          h[1][imageIn.getGComponent(x, y)]++;
	  	          h[2][imageIn.getBComponent(x, y)]++;
			 }
	    }
	    int[] percentileColor = GetPercentileColor(h, 0.005f);
	    int[] meanColor = GetMeanColor(h);
	    int[] hi = GetPercentileColor(h, 0.995f);
	    float[] gamma = ComputeGamma(percentileColor, meanColor, hi);
	    for (int i = 0; i < 3; i++){
	        for (int j = 0; j < 256; j++){
	            int[] arr = new int[3];
	            for (int n = 0; n < 3; n++){
	                float percent = j - percentileColor[n];
	                if (percent < 0f){
	                    arr[n] = array[n];
	                }
	                else if ((percent + percentileColor[n]) >= hi[n]){
	                    arr[n] = rgb[n];
	                }
	                else {
	                    double adjust = array[n] + ((rgb[n] - array[n]) * Math.pow((double) (percent / ((float) (hi[n] - percentileColor[n]))), (double) gamma[n]));
	                    arr[n] = (adjust > 255.0) ? ((int) 255.0) : ((adjust < 0.0) ? ((int) 0.0) : ((int) adjust));
	                }
	            }
	            rr[j] = arr[0];
	            gg[j] = arr[1];
	            bb[j] = arr[2];
	        }
	    }
	    Image clone = imageIn.clone();
	    int r,g,b;
	    for (int x = 0; x < imageIn.getWidth() - 1; x++){
  			 for (int y = 0; y < imageIn.getHeight() - 1; y++)  {  
  				r = clone.getRComponent(x, y);
  				g = clone.getGComponent(x, y);
  				b = clone.getBComponent(x, y);
		        r = (r * intensity_invert + rr[r] * intensity) >> 8;
		        g = (g * intensity_invert + gg[g] * intensity) >> 8;
		        b = (b * intensity_invert + bb[b] * intensity) >> 8;
		        imageIn.setPixelColor(x, y, r, g, b);
		 	 }
	    }
	    return imageIn;//��ֱ��ͼģʽ��ǿ
	}

}
