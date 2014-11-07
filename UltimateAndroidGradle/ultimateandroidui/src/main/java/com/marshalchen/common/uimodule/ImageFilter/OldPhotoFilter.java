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

public class OldPhotoFilter implements IImageFilter{

	private GaussianBlurFilter blurFx;
	private NoiseFilter noiseFx;
	private VignetteFilter vignetteFx;
	private GradientMapFilter gradientFx;
	public OldPhotoFilter(){
		blurFx = new GaussianBlurFilter();
		blurFx.Sigma = 0.3f;
		  
		noiseFx = new NoiseFilter();
		noiseFx.Intensity = 0.03f;
		
		vignetteFx = new VignetteFilter();
		vignetteFx.Size = 0.6f;
		
		gradientFx = new GradientMapFilter();
		gradientFx.ContrastFactor = 0.3f;
	}
	
    //@Override
    public Image process(Image imageIn) {
    	imageIn = this.noiseFx.process(this.blurFx.process(imageIn));
    	imageIn = this.gradientFx.process(imageIn);
    	return this.vignetteFx.process(imageIn);
    }
}
