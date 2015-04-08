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

import android.graphics.Color;

import com.marshalchen.common.uimodule.ImageFilter.Gradient.TintColors;
import com.marshalchen.common.uimodule.ImageFilter.ImageBlender.BlendMode;

import java.util.ArrayList;
import java.util.List;

/**
 * X��Ч��
 * @author daizhj
 *
 */
public class XRadiationFilter implements IImageFilter{

	private GradientMapFilter gradientMapFx = new GradientMapFilter();
	private ImageBlender blender = new ImageBlender();

	public XRadiationFilter(){
	    List<Integer> colors = new ArrayList<Integer>();
	    colors.add(TintColors.LightCyan());
	    colors.add(Color.BLACK);
	    gradientMapFx.Map = new Gradient(colors);
	    blender.Mode = BlendMode.ColorBurn;
	    blender.Mixture = 0.8f;
	}

	 //@Override
    public Image process(Image imageIn) {
    	imageIn = this.gradientMapFx.process(imageIn);
    	imageIn = this.blender.Blend(imageIn, imageIn);
    	return imageIn;
    }
}
