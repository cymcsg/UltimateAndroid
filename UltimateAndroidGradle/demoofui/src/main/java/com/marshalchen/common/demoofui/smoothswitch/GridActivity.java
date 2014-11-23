package com.marshalchen.common.demoofui.smoothswitch;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.smoothswitch.SwitchAnimationUtil;
import com.marshalchen.common.uimodule.smoothswitch.SwitchAnimationUtil.*;

public class GridActivity extends Activity {
    private GridView mGrid;
    private SwitchAnimationUtil mSwitchAnimationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_animation_activity_grid);
        mGrid = (GridView) findViewById(R.id.grid);
        mGrid.setAdapter(new GridAdapter());

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
        Intent intent = new Intent(GridActivity.this, ListActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mSwitchAnimationUtil == null) {
            mSwitchAnimationUtil = new SwitchAnimationUtil();
            mSwitchAnimationUtil.startAnimation(getWindow().getDecorView(), Constant.mType);
        }
    }

    private class GridAdapter extends BaseAdapter {
        private int[] res = new int[] { R.drawable.test, R.drawable.test_back1, R.drawable.test_back2, R.drawable.test_back };

        @Override
        public int getCount() {
            return 99;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            if (convertView == null) {
                ImageView mImage = new ImageView(GridActivity.this);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(440 / 4, 440 / 4);
                mImage.setLayoutParams(params);
                mImage.setScaleType(ScaleType.CENTER_CROP);
                mImage.setPadding(5, 5, 5, 5);
                convertView = mImage;
                convertView.setAlpha(0);
            }

            ((ImageView) convertView).setImageResource(res[position % res.length]);
            return convertView;
        }
    }
}
