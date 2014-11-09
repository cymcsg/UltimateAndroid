package com.marshalchen.common.uimodule.tileView.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewCurator {

	private ViewCurator() {
		
	}
	
	public static void clear( View view ) {
		if ( view instanceof ImageView ) {
			ImageView imageView = (ImageView) view;
			imageView.setImageBitmap( null );
		} else if ( view instanceof ViewGroup ) {
			ViewGroup viewGroup = (ViewGroup) view;
			int childCount = viewGroup.getChildCount();
			for ( int i = 0; i < childCount; i++ ) {
				View child = viewGroup.getChildAt( i );
				clear( child );
			}
			viewGroup.removeAllViews();
		}		
	}
}
