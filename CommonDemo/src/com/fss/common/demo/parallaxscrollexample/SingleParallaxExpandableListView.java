package com.fss.common.demo.parallaxscrollexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.fss.Common.uiModule.parallaxscroll.ParallaxExpandableListView;
import com.fss.common.demo.R;

public class SingleParallaxExpandableListView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parallax_scroll_expand_list_one_parallax);
		ParallaxExpandableListView listView = (ParallaxExpandableListView) findViewById(R.id.list_view);
		
		TextView v = new TextView(this);
		v.setText("PARALLAXED");
		v.setGravity(Gravity.CENTER);
		v.setTextSize(40);
		v.setHeight(200);
		v.setBackgroundResource(R.drawable.parallax_scroll_item_background);
		
		listView.addParallaxedHeaderView(v);
		CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(LayoutInflater.from(this));
		listView.setAdapter(adapter);
	}
	

}
