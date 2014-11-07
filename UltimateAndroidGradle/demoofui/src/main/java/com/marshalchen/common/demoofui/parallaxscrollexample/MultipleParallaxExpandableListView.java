package com.marshalchen.common.demoofui.parallaxscrollexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.marshalchen.common.uimodule.parallaxscroll.ParallaxExpandableListView;
import com.marshalchen.common.demoofui.R;

public class MultipleParallaxExpandableListView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parallax_scroll_expand_list_multiple_parallax);
		ParallaxExpandableListView listView = (ParallaxExpandableListView) findViewById(R.id.list_view);
		CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(LayoutInflater.from(this));
		listView.setAdapter(adapter);
	}
	

}
