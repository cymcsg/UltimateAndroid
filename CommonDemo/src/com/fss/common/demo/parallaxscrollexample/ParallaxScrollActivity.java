package com.fss.common.demo.parallaxscrollexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.fss.Common.ui.HomeasUpActionbarActivity;
import com.fss.common.demo.R;

public class ParallaxScrollActivity extends HomeasUpActionbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parallax_scroll_activity_main);
    }

    public void onSingleScrollParallaxClicked(View v) {
        Intent intent = new Intent(this, SingleParallaxScrollView.class);
        startActivity(intent);
    }

    public void onMultipleScrollParallaxClicked(View v) {
        Intent intent = new Intent(this, MultipleParallaxScrollView.class);
        startActivity(intent);
    }

    public void onSingleListParallaxClicked(View v) {
        Intent intent = new Intent(this, SingleParallaxListView.class);
        startActivity(intent);
    }

    public void onSingleExpandableListParallaxClicked(View v) {
        Intent intent = new Intent(this, SingleParallaxExpandableListView.class);
        startActivity(intent);
    }

    public void onMultipleExpandableListParallaxClicked(View v) {
        Intent intent = new Intent(this, MultipleParallaxExpandableListView.class);
        startActivity(intent);
    }

    public void onMultipleListParallaxClicked(View v) {
        Intent intent = new Intent(this, MultipleParallaxListView.class);
        startActivity(intent);
    }


}