package com.marshalchen.common.demoofui.kugoulayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.kugouLayout.KugouLayout;


/**
 * Created by zzt on 2015/2/14.
 */
public class KugouLayoutActivityDemo extends ActionBarActivity {

    private KugouLayout kugouLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kugou_layout_activity2);

        kugouLayout = (KugouLayout)findViewById(R.id.kugoulayout);

        kugouLayout.setContentView(R.layout.kugou_layout_activity_main);
        kugouLayout.setAnimType(KugouLayout.REBOUND_ANIM);
        kugouLayout.addHorizontalScrollableView(kugouLayout.findViewById(R.id.horizontalScrollView));
        kugouLayout.setLayoutCloseListener(new KugouLayout.LayoutCloseListener() {
            @Override
            public void onLayoutClose() {
                System.out.println("you do something here when layout close");
            }
        });

        KugouLayout kugouLayout1 = (KugouLayout)findViewById(R.id.kugoulayout2);
        kugouLayout1.setContentView(R.layout.kugou_layout_imagelayout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kugou_layout_menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_normal_anim:
                kugouLayout.setAnimType(KugouLayout.NORMAL_ANIM);
                return true;
            case R.id.action_rebound_anim:
                kugouLayout.setAnimType(KugouLayout.REBOUND_ANIM);
                return true;
            case R.id.action_always_rebound_anim:
                kugouLayout.setAnimType(KugouLayout.ALWAYS_REBOUND);
                return true;
            case R.id.action_show:
                kugouLayout.show();
                return true;
        }
        return false;
    }
}
