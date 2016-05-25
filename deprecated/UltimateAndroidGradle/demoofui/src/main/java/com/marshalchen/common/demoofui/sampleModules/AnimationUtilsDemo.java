package com.marshalchen.common.demoofui.sampleModules;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.marshalchen.common.commonUtils.basicUtils.HandlerUtils;
import com.marshalchen.common.commonUtils.uiUtils.AnimationUtils;
import com.marshalchen.common.demoofui.R;


public class AnimationUtilsDemo extends ActionBarActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_utils_demo);
        imageView = (ImageView) findViewById(R.id.animationUtilsDemoImageView);
        HandlerUtils.sendMessageHandlerDelay(shakeHandler, 0, 1000);
        HandlerUtils.sendMessageHandlerDelay(leftShakeHandler, 0, 3000);

    }

    Handler shakeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AnimationUtils.wholeShake(imageView).start();
        }
    };

    Handler leftShakeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AnimationUtils.leftRightShake(imageView).start();
        }
    };

}
