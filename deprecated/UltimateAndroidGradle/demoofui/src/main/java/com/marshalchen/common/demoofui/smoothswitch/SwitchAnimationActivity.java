package com.marshalchen.common.demoofui.smoothswitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.smoothswitch.SwitchAnimationUtil;
import com.marshalchen.common.uimodule.smoothswitch.SwitchAnimationUtil.*;

public class SwitchAnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_animation_activity_main);
        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(), Constant.mType);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.switch_animation_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_alpha:
            Constant.mType = AnimationType.ALPHA;
            break;
        case R.id.action_flip_horizon:
            Constant.mType = AnimationType.FLIP_HORIZON;
            break;
        case R.id.action_flip_vertical:
            Constant.mType = AnimationType.FLIP_VERTICAL;
            break;
        case R.id.action_horizon_left:
            Constant.mType = AnimationType.HORIZION_LEFT;
            break;
        case R.id.action_horizon_right:
            Constant.mType = AnimationType.HORIZION_RIGHT;
            break;
        case R.id.action_rotate:
            Constant.mType = AnimationType.ROTATE;
            break;
        case R.id.action_scale:
            Constant.mType = AnimationType.SCALE;
            break;
        case R.id.action_cross:
            Constant.mType = AnimationType.HORIZON_CROSS;
            break;
        case R.id.action_next:
            break;
        }
        Intent intent = new Intent(SwitchAnimationActivity.this, GridActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
