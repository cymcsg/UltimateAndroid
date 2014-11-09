package com.marshalchen.common.demoofui.parallaxscrollexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.marshalchen.common.uimodule.parallaxscroll.ParallaxListView;
import com.marshalchen.common.demoofui.R;


public class SingleParallaxListView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parallax_scroll_list_one_parallax);
		ParallaxListView listView = (ParallaxListView) findViewById(R.id.list_view);
		CustomListAdapter adapter = new CustomListAdapter(LayoutInflater.from(this));
		
		TextView v = new TextView(this);
		v.setText("PARALLAXED");
		v.setGravity(Gravity.CENTER);
		v.setTextSize(40);
		v.setHeight(200);
		v.setBackgroundResource(R.drawable.parallax_scroll_item_background);
		
		listView.addParallaxedHeaderView(v);
		listView.setAdapter(adapter);
	}
	

}
