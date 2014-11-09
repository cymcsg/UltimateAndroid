package com.marshalchen.common.uimodule.tileView.tileview.samples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevel;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailLevelEventListener;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailManager;
import com.marshalchen.common.uimodule.tileView.tileview.graphics.BitmapDecoder;
import com.marshalchen.common.uimodule.tileView.tileview.graphics.BitmapDecoderAssets;

public class SampleManager extends View implements DetailLevelEventListener {

	private DetailManager detailManager;
	private BitmapDecoder decoder = new BitmapDecoderAssets();
	
	private Rect area = new Rect(0, 0, 0, 0);
	
	private Bitmap bitmap;
	private String lastFileName;
	
	public SampleManager( Context context, DetailManager dm ) {
		
		super( context );
		
		detailManager = dm;
		detailManager.addDetailLevelEventListener( this );
		
		update();
		
	}
	
	public void setDecoder( BitmapDecoder d ){
		decoder = d;
	}
	
	public void clear(){
		bitmap = null;
		lastFileName = null;
	}

	public void update() {
		DetailLevel detailLevel = detailManager.getCurrentDetailLevel();
		if( detailLevel != null ) {
			String fileName = detailLevel.getDownsample();
			if( fileName != null ) {
				if( !fileName.equals( lastFileName ) ) {
					bitmap = decoder.decode( fileName, getContext() );
					invalidate();
				}
			}
			lastFileName = fileName;
		}		
	}
	
	@Override
	public void onDetailLevelChanged() {
		update();
	}

	@Override
	public void onDetailScaleChanged( double s ) {
		
	}

	@Override
	public void onDraw( Canvas canvas ) {
		if( bitmap != null) {
			area.right = getWidth();
			area.bottom = getHeight();
			canvas.drawBitmap( bitmap, null, area, null);
		}		
		super.onDraw( canvas );
	}
}
