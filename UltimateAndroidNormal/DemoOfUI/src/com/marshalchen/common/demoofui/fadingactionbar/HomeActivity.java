/*
 * Copyright (c) 2014. Marshal Chen.
 */
package com.marshalchen.common.demoofui.fadingactionbar;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.marshalchen.common.demoofui.R;

public class HomeActivity extends ListActivity {
    private List<ActivityInfo> activitiesInfo = Arrays.asList(
            new ActivityInfo(ScrollViewActivity.class, R.string.activity_title_scrollview),
            new ActivityInfo(ListViewActivity.class, R.string.activity_title_listview),
            new ActivityInfo(LightBackgroundActivity.class, R.string.activity_title_light_bg),
            new ActivityInfo(LightActionBarActivity.class, R.string.activity_title_light_ab),
            new ActivityInfo(SampleFragmentActivity.class, R.string.activity_title_fragment),
            new ActivityInfo(NoParallaxActivity.class, R.string.activity_title_no_parallax),
            new ActivityInfo(NavigationDrawerActivity.class, R.string.activity_title_navigation),
            new ActivityInfo(HeaderOverlayActivity.class, R.string.activity_title_header_overlay),
            new ActivityInfo(WebViewActivity.class, R.string.activity_title_webview),
            new ActivityInfo(ShortContentActivity.class, R.string.activity_title_short_content));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fading_actionbar_activity_home);
        String[] titles = getActivityTitles();
        setListAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, android.R.id.text1, titles));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Class<? extends Activity> class_ = activitiesInfo.get(position).activityClass;
        Intent intent = new Intent(this, class_);
        startActivity(intent);
    }

    private String[] getActivityTitles() {
        String[] result = new String[activitiesInfo.size()];
        int i = 0;
        for (ActivityInfo info : activitiesInfo) {
            result[i++] = getString(info.titleResourceId);
        }
        return result;
    }
}
