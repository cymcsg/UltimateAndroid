package com.marshalchen.common.demoofui.parallaxscrollexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.marshalchen.common.ui.HomeasUpActionbarActivity;
import com.marshalchen.common.demoofui.R;

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