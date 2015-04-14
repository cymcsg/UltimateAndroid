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

package com.marshalchen.common.uimodule.ImageFilter.Textures;

import com.marshalchen.common.uimodule.ImageFilter.NoiseFilter;

public class MarbleTexture implements ITextureGenerator
{
	// Perlin noise function used for texture generation
    private PerlinNoise noise = new PerlinNoise( 1.0 / 32, 1.0, 0.65, 2 );

 	private int	r;

	private double	xPeriod = 5.0;
	private double	yPeriod = 10.0;



    /// <summary>
    /// Initializes a new instance of the <see cref="MarbleTexture"/> class
    /// </summary>
    /// 
	public MarbleTexture( )
    {
        Reset( );
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="MarbleTexture"/> class
    /// </summary>
    /// 
    /// <param name="xPeriod">XPeriod value</param>
    /// <param name="yPeriod">YPeriod value</param>
    /// 
	public MarbleTexture( double xPeriod, double yPeriod )
	{
		this.xPeriod =  Math.max( 2.0, xPeriod);
		this.yPeriod =  Math.max( 2.0, yPeriod);
		Reset( );
	}

    /// <summary>
    /// Generate texture
    /// </summary>
    /// 
    /// <param name="width">Texture's width</param>
    /// <param name="height">Texture's height</param>
    /// 
    /// <returns>Two dimensional array of intensities</returns>
    /// 
    /// <remarks>Generates new texture with specified dimension.</remarks>
    /// 
    public float[][] Generate( int width, int height )
	{
		float[][]	texture = new float[height][width];
		double		xFact = xPeriod / width;
		double		yFact = yPeriod / height;

		for ( int y = 0; y < height; y++ )
		{
			for ( int x = 0; x < width; x++ )
			{
				texture[y][x] = 
					Math.min( 1.0f, (float)
						Math.abs( Math.sin( 
							( x * xFact + y * yFact + noise.Function2D( x + r, y + r ) ) * Math.PI
						) )
					);

			}
		}
		return texture;
	}
    
  

    /// <summary>
    /// Reset generator
    /// </summary>
    /// 
    /// <remarks>Regenerates internal random numbers.</remarks>
    /// 
    public void Reset( )
	{
		r = NoiseFilter.getRandomInt(1, 5000 );
	}
}
