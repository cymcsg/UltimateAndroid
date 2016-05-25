package com.marshalchen.common.demoofui.activityanimation;

import android.os.Bundle;
import com.marshalchen.common.uimodule.activityanimation.AnimatedDoorActivity;
import com.marshalchen.common.demoofui.R;


public class DoorActivity extends AnimatedDoorActivity {

    @Override
    protected int layoutResId() {
        return R.layout.act_animation_door;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
