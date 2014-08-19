package com.fss.common.demo.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.fss.common.demo.R;
import com.fss.common.ui.PullToZoomListView;


public class PulltoZoomListViewActivity extends Activity {
	PullToZoomListView listView;
	private String[] adapterData; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (PullToZoomListView)findViewById(R.id.listview);
        adapterData = new String[] { "Activity","Service","Content Provider","Intent","BroadcastReceiver","ADT","Sqlite3","HttpClient","DDMS","Android Studio","Fragment","Loader" }; 
 
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, adapterData));
        listView.getHeaderView().setImageResource(R.drawable.splash01);
        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
