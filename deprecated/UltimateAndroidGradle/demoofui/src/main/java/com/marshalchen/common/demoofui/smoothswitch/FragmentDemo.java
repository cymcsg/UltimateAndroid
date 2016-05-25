package com.marshalchen.common.demoofui.smoothswitch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.smoothswitch.SwitchAnimationUtil;
import com.marshalchen.common.uimodule.smoothswitch.SwitchAnimationUtil.*;


public class FragmentDemo extends FragmentActivity {
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_animation_activity_fragment);
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, new FlowerFragment());
        mFragmentTransaction.commit();
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
            Constant.mType = SwitchAnimationUtil.AnimationType.FLIP_HORIZON;
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
        Intent intent = new Intent(FragmentDemo.this, SwitchAnimationActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}


@SuppressLint("ValidFragment")
class FlowerFragment extends Fragment {
    private View mConverView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mConverView = LayoutInflater.from(getActivity()).inflate(R.layout.switch_animation_view_fragment, null);
        mConverView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                new SwitchAnimationUtil().startAnimation(mConverView, Constant.mType);
            }
        });

        return mConverView;
    }

}
