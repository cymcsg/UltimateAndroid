package com.fss.commons.demo.activityanimation;

import android.os.Bundle;
import com.fss.commons.uiModule.activityanimation.AnimatedDoorActivity;
import com.fss.commons.demo.R;


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
