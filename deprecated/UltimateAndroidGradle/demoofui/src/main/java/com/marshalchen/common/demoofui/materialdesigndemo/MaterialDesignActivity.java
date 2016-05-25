package com.marshalchen.common.demoofui.materialdesigndemo;

import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.LayoutRipple;
import com.gc.materialdesign.widgets.ColorSelector;
import com.gc.materialdesign.widgets.ColorSelector.OnColorSelectedListener;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.demoofui.HowToUseActivity;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.swipelayoutdemo.GridViewExample;
import com.marshalchen.common.demoofui.swipelayoutdemo.ListViewExample;
import com.marshalchen.common.demoofui.swipelayoutdemo.NestedExample;
import com.nineoldandroids.view.ViewHelper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;


public class MaterialDesignActivity extends Activity implements OnColorSelectedListener {

    int backgroundColor = Color.parseColor("#1E88E5");
    ButtonFloatSmall buttonSelectColor;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_activity_main);

        buttonSelectColor = (ButtonFloatSmall) findViewById(R.id.buttonColorSelector);
        buttonSelectColor.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ColorSelector colorSelector = new ColorSelector(MaterialDesignActivity.this, backgroundColor, MaterialDesignActivity.this);
                colorSelector.show();
            }
        });

        LayoutRipple layoutRipple = (LayoutRipple) findViewById(R.id.itemButtons);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaterialDesignActivity.this, ButtonsActivity.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        layoutRipple = (LayoutRipple) findViewById(R.id.itemSwitches);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaterialDesignActivity.this, SwitchActivity.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        layoutRipple = (LayoutRipple) findViewById(R.id.itemProgress);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaterialDesignActivity.this, ProgressActivity.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        layoutRipple = (LayoutRipple) findViewById(R.id.itemWidgets);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaterialDesignActivity.this, WidgetActivity.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
    }

    private void setOriginRiple(final LayoutRipple layoutRipple) {

        layoutRipple.post(new Runnable() {

            @Override
            public void run() {
                View v = layoutRipple.getChildAt(0);
                layoutRipple.setxRippleOrigin(ViewHelper.getX(v) + v.getWidth() / 2);
                layoutRipple.setyRippleOrigin(ViewHelper.getY(v) + v.getHeight() / 2);

                layoutRipple.setRippleColor(Color.parseColor("#1E88E5"));

                layoutRipple.setRippleSpeed(30);
            }
        });

    }

    @Override
    public void onColorSelected(int color) {
        backgroundColor = color;
        buttonSelectColor.setBackgroundColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_how_to_use, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.howToUse) {
            BasicUtils.sendIntent(this, HowToUseActivity.class, "data", "MaterialDesignActivity");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
