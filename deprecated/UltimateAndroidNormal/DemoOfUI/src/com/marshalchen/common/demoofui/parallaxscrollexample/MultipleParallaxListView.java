package com.marshalchen.common.demoofui.parallaxscrollexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.marshalchen.common.uimodule.parallaxscroll.ParallaxListView;
import com.marshalchen.common.demoofui.R;


public class MultipleParallaxListView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parallax_scroll_list_multiple_parallax);
		ParallaxListView listView = (ParallaxListView) findViewById(R.id.list_view);
		CustomListAdapter adapter = new CustomListAdapter(LayoutInflater.from(this));
		listView.setAdapter(adapter);
	}
	

}
