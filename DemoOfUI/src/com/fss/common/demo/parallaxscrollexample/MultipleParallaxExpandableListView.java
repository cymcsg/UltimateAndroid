package com.fss.common.demo.parallaxscrollexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.fss.common.uiModule.parallaxscroll.ParallaxExpandableListView;
import com.fss.common.demo.R;

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
