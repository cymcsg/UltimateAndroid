package com.marshalchen.common.demoofui.parallaxswipeback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.ParallaxSwipeBackActivity;

/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: 主界面
 * Created by bushijie33@gmail.com on 2015/7/5.
 */
public class ParallaxSwipeBackDemoActivity extends ParallaxSwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parallax_swipe_back_activity_main);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParallaxSwipeBackDemoActivity.this,ParallaxSwipeBackNextActivity.class);
                intent.putExtra("index",0);
                startParallaxSwipeBackActivty(ParallaxSwipeBackDemoActivity.this, intent);
            }
        });

    }

}
