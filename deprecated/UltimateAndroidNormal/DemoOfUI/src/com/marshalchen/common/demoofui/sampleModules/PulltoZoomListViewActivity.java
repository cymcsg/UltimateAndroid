package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.PullToZoomListView;


public class PulltoZoomListViewActivity extends Activity {
    PullToZoomListView listView;
    private String[] adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_to_zoom_list_view_activity);
        listView = (PullToZoomListView) findViewById(R.id.listview);
        adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "DDMS", "Android Studio", "Fragment", "Loader"};

        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, adapterData));
        listView.getHeaderView().setImageResource(R.drawable.test_back);
        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
