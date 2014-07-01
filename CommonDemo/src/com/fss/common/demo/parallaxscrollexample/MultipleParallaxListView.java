package com.fss.common.demo.parallaxscrollexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.fss.Common.uiModule.parallaxscroll.ParallaxListView;
import com.fss.common.demo.R;


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
