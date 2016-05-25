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
 * ����Ч��
 * @author daizhj
 *
 */
public class ComicFilter implements IImageFilter{

	 SaturationModifyFilter saturationFx = new SaturationModifyFilter();
     GaussianBlurFilter blurFx = new GaussianBlurFilter();
     ImageBlender blender = new ImageBlender();
     ParamEdgeDetectFilter edgeDetectionFx = new ParamEdgeDetectFilter();
     ImageBlender edgeBlender = new ImageBlender();

     public ComicFilter()
     {
         saturationFx.SaturationFactor = 1f;
         blurFx.Sigma = 1f;
         blender.Mixture = 1f;
         blender.Mode = BlendMode.Lighten;
         edgeDetectionFx.Threshold = 0.25f;
         edgeDetectionFx.DoGrayConversion = true;
         edgeBlender.Mixture = 0.8f;
         edgeBlender.Mode = BlendMode.Lighten;
     }

     public Image process(Image input)
     {
         Image saturated = saturationFx.process(input.clone());
         Image blurred = blurFx.process(saturated);
         input = blender.Blend(saturated, blurred);
         Image edge = edgeDetectionFx.process(input.clone());
         return edgeBlender.Blend(input, edge);
     }
}
