package com.marshalchen.common.demoofui.pullscrollview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.marshalchen.common.demoofui.R;


/**
 * Demo
 *
 * @author markmjw
 * @date 2014-04-30
 */
public class PullScrollViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_scroll_view_act_main);

        findViewById(R.id.pulldown_scrollview_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PullScrollViewActivity.this, PulldownViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.stretch_scrollview_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PullScrollViewActivity.this, StretchViewActivity.class);
                startActivity(intent);
            }
        });
    }

}
