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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.marshalchen.common.uimodule.ImageFilter.ImageBlender.BlendMode;
import android.util.Log;

/**
 * �龰Ч��������Ȼ��ɫ��Ⱦ��
 * @author daizhj
 *
 */
public class SceneFilter implements IImageFilter{
	private GradientFilter gradientFx;
    private SaturationModifyFilter saturationFx;

    public SceneFilter(float angle, Gradient gradient)
    {
        gradientFx = new GradientFilter();
        gradientFx.Gradient = gradient;
        gradientFx.OriginAngleDegree = angle;

        saturationFx = new SaturationModifyFilter();
        saturationFx.SaturationFactor = -0.6f;
    }

    //@Override
    public Image process(Image imageIn)
    {
        Image clone = imageIn.clone();
        imageIn = gradientFx.process(imageIn);
        ImageBlender blender = new ImageBlender();
        blender.Mode = BlendMode.Subractive;
        return saturationFx.process(blender.Blend(clone, imageIn));
        //return imageIn;// saturationFx.process(imageIn);
    }
}
