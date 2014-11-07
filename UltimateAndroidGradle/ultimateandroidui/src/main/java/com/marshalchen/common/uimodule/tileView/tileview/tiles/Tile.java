package com.marshalchen.common.uimodule.tileView.tileview.tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import com.marshalchen.common.uimodule.tileView.tileview.graphics.BitmapDecoder;

public class Tile {

	private int left;
	private int top;
	private int width;
	private int height;
	private String file;

	private ImageView imageView;
	private Bitmap bitmap;

	private boolean hasBitmap;

	public Tile() {

	}

	public Tile( int l, int t, int w, int h, String f ) {
		left = l;
		top = t;
		width = w;
		height = h;
		file = f;
	}

	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public String getFileName() {
		return file;
	}

	public void decode( Context context, TileCache cache, BitmapDecoder decoder ) {
		if ( hasBitmap ) {
			return;
		}
		if ( cache != null ) {
			Bitmap cached = cache.getBitmap( file );
			if ( cached != null ) {
				bitmap = cached;
				return;
			}
		}
		bitmap = decoder.decode( file, context );
		hasBitmap = ( bitmap != null );
		if ( cache != null ) {
			cache.addBitmap( file, bitmap );
		}
	}

	public void render( Context context ) {
		if ( imageView == null ) {
			imageView = new ImageView( context );
			imageView.setAdjustViewBounds( false );
			imageView.setScaleType( ImageView.ScaleType.MATRIX );
		}		
		imageView.setImageBitmap( bitmap );
	}

	public void destroy() {
		if ( imageView != null ) {
			imageView.clearAnimation();
			imageView.setImageBitmap( null );
			ViewParent parent = imageView.getParent();
			if ( parent != null && parent instanceof ViewGroup ) {
				ViewGroup group = (ViewGroup) parent;
				group.removeView( imageView );
			}
			imageView = null;
		}
		bitmap = null;
		hasBitmap = false;
	}

	@Override
	public boolean equals( Object o ) {
		if ( o instanceof Tile ) {
			Tile m = (Tile) o;
			return ( m.getLeft() == getLeft() )
					&& ( m.getTop() == getTop() )
					&& ( m.getWidth() == getWidth() )
					&& ( m.getHeight() == getHeight() )
					&& ( m.getFileName().equals( getFileName() ) );
		}
		return false;
	}

	@Override
	public String toString() {
		return "(left=" + left + ", top=" + top + ", width=" + width + ", height=" + height + ", file=" + file + ")";
	}

}
