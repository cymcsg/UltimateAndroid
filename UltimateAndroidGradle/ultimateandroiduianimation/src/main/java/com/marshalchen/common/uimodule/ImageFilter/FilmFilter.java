/* 
 * HaoRan ImageFilter Classes v0.2
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

import com.marshalchen.common.uimodule.ImageFilter.ImageBlender.BlendMode;

/**
 * ��ӰԺЧ��
 * @author daizhj
 *
 */
public class FilmFilter implements IImageFilter{
	private GradientFilter gradient;
    private SaturationModifyFilter saturationFx;

    public FilmFilter(float angle)
    {
        gradient = new GradientFilter();
        gradient.Gradient = Gradient.Fade();
        gradient.OriginAngleDegree = angle;

        saturationFx = new SaturationModifyFilter();
        saturationFx.SaturationFactor = -0.6f;
    }

    //@Override
    public Image process(Image imageIn)
    {
        Image clone = imageIn.clone();
        imageIn = gradient.process(imageIn);
        ImageBlender blender = new ImageBlender();
        blender.Mode = BlendMode.Multiply;
        return saturationFx.process(blender.Blend(clone, imageIn));
        //return imageIn;// saturationFx.process(imageIn);
    }

}
