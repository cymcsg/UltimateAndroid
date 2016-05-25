package com.marshalchen.common.demoofui.materialAnimations;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.marshalchen.common.demoofui.R;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DetailActivity2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_animation_activity_details2);
    }
}
