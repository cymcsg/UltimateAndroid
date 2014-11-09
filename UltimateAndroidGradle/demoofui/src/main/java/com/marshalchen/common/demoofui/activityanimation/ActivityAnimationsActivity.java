package com.marshalchen.common.demoofui.activityanimation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import com.marshalchen.common.uimodule.activityanimation.AnimatedDoorLayout;
import com.marshalchen.common.uimodule.activityanimation.AnimatedRectLayout;
import com.marshalchen.common.demoofui.R;


public class ActivityAnimationsActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private RadioButton mRandomAnim;
    private RadioButton mWaveTlAnim;
    private RadioButton mWaveBrAnim;
    private RadioButton mWaveTrAnim;
    private RadioButton mWaveBlAnim;
    private Button mNext;

    private RadioButton mVerticalDoor;
    private RadioButton mHorizontalDoor;
    private Button mOpenDoor;

    private int mAnimationType = AnimatedRectLayout.ANIMATION_RANDOM;
    private int mDoorType = AnimatedDoorLayout.HORIZONTAL_DOOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_animation_activity);
        findViews();
        mNext.setOnClickListener(this);
        mOpenDoor.setOnClickListener(this);

        mRandomAnim.setOnCheckedChangeListener(this);
        mWaveTlAnim.setOnCheckedChangeListener(this);
        mWaveBrAnim.setOnCheckedChangeListener(this);
        mWaveTrAnim.setOnCheckedChangeListener(this);
        mWaveBlAnim.setOnCheckedChangeListener(this);
        mHorizontalDoor.setOnCheckedChangeListener(this);
        mVerticalDoor.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if(view.getId() == R.id.next) {
            intent = new Intent(this, SecondActivity.class);
            intent.putExtra("animation_type", mAnimationType);
        } else if(view.getId() == R.id.open_door) {
            intent = new Intent(this, DoorActivity.class);
            intent.putExtra("door_type", mDoorType);
        }
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void findViews() {
        mRandomAnim = (RadioButton) findViewById(R.id.random_anim);
        mWaveTlAnim = (RadioButton) findViewById(R.id.wave_tl_anim);
        mWaveBrAnim = (RadioButton) findViewById(R.id.wave_br_anim);
        mWaveTrAnim = (RadioButton) findViewById(R.id.wave_tr_anim);
        mWaveBlAnim = (RadioButton) findViewById(R.id.wave_bl_anim);
        mNext = (Button) findViewById(R.id.next);
        //
        mHorizontalDoor = (RadioButton) findViewById(R.id.horizontal_door);
        mVerticalDoor = (RadioButton) findViewById(R.id.vertical_door);
        mOpenDoor = (Button) findViewById(R.id.open_door);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if(id == R.id.random_anim && isChecked) {
            mAnimationType = AnimatedRectLayout.ANIMATION_RANDOM;
        } else if(id == R.id.wave_tl_anim && isChecked) {
            mAnimationType = AnimatedRectLayout.ANIMATION_WAVE_TL;
        } else if(id == R.id.wave_br_anim && isChecked) {
            mAnimationType = AnimatedRectLayout.ANIMATION_WAVE_BR;
        } else if(id == R.id.wave_bl_anim && isChecked) {
            mAnimationType = AnimatedRectLayout.ANIMATION_WAVE_BL;
        } else if(id == R.id.wave_tr_anim && isChecked) {
            mAnimationType = AnimatedRectLayout.ANIMATION_WAVE_TR;
        } else if(id == R.id.horizontal_door && isChecked) {
            mDoorType = AnimatedDoorLayout.HORIZONTAL_DOOR;
        } else if(id == R.id.vertical_door && isChecked) {
            mDoorType = AnimatedDoorLayout.VERTICAL_DOOR;
        }
    }
}
