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

public class TextileTexture implements ITextureGenerator
{
	 // Perlin noise function used for texture generation
    private PerlinNoise noise = new PerlinNoise( 1.0 / 8, 1.0, 0.65, 3 );

    // randmom number generator
    private int r;

    /// <summary>
    /// Initializes a new instance of the <see cref="TextileTexture"/> class
    /// </summary>
    /// 
	public TextileTexture( )
	{
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
		float[][] texture = new float[height][width];

		for ( int y = 0; y < height; y++ )
		{
			for ( int x = 0; x < width; x++ )
			{
				texture[y][x] = 
					Math.max( 0.0f, Math.min( 1.0f,
						(
							(float) Math.sin( x + noise.Function2D( x + r, y + r ) ) +
							(float) Math.sin( y + noise.Function2D( x + r, y + r ) )
						) * 0.25f + 0.5f
					) );

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
