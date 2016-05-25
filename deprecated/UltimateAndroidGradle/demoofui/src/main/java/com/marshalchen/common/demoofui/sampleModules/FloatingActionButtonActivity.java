package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.FloatActionButton;

public class FloatingActionButtonActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_action_button_activity);
        FloatActionButton fabButton = new FloatActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ico_arrow2x))
                .withButtonColor(Color.WHITE)
                .withGravity(Gravity.CENTER | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();
    }
}
