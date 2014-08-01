package com.fss.common.demo.activityanimation;

import android.os.Bundle;
import com.fss.common.uiModule.activityanimation.AnimatedDoorActivity;
import com.fss.common.demo.R;


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
