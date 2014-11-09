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

import java.util.List;

import com.marshalchen.common.uimodule.ImageFilter.ImageBlender.BlendMode;


public class RainBowFilter implements IImageFilter{

	public ImageBlender blender = new ImageBlender();
	public boolean IsDoubleRainbow = false;
	private GradientFilter gradientFx;
	public float gradAngleDegree = 40f; 
	
	public RainBowFilter(){
		    blender.Mixture = 0.25f;
		    blender.Mode = BlendMode.Additive;
	
		    IsDoubleRainbow = true;
		   
            List<Integer> rainbowColors = Gradient.RainBow().MapColors;
            if (this.IsDoubleRainbow)
            {
                rainbowColors.remove(rainbowColors.size() - 1);//remove red
                rainbowColors.addAll(Gradient.RainBow().MapColors);
            }  
		    gradientFx = new GradientFilter();
		    gradientFx.OriginAngleDegree = gradAngleDegree;
		    gradientFx.Gradient = new Gradient(rainbowColors);
    }
	
	 //@Override
    public Image process(Image imageIn) {
    	Image clone =  gradientFx.process(imageIn.clone());
        return blender.Blend(imageIn, clone);
    }
}
