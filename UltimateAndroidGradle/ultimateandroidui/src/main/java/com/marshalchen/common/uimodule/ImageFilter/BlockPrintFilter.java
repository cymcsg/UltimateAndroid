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

import com.marshalchen.common.uimodule.ImageFilter.ImageBlender.BlendMode;

/**
 * �滭Ч��
 * @author daizhj
 *
 */
public class BlockPrintFilter implements IImageFilter {
	//@Override
    public Image process(Image imageIn) {
    	ParamEdgeDetectFilter pde = new ParamEdgeDetectFilter();
    	pde.K00 = 1;
    	pde.K01 = 2;
    	pde.K02 = 1;
    	pde.Threshold = 0.25f;
        pde.DoGrayConversion = false;
        ImageBlender ib = new ImageBlender();
        ib.Mode = (int)BlendMode.Multiply;
        return ib.Blend(imageIn.clone(), pde.process(imageIn));
    }
}
