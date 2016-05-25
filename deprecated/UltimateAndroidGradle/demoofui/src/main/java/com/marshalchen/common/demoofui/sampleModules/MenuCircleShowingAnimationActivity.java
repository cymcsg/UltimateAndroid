package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.utils.MenuCircleShowingAnimation;


public class MenuCircleShowingAnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_circle_animation_main);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);

        MenuCircleShowingAnimation promotedActionsLibrary = new MenuCircleShowingAnimation();

        promotedActionsLibrary.setup(getApplicationContext(), frameLayout);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Button clicked.", Toast.LENGTH_SHORT).show();
            }
        };

        promotedActionsLibrary.addItem(getResources().getDrawable(android.R.drawable.ic_menu_edit), onClickListener);
        promotedActionsLibrary.addItem(getResources().getDrawable(android.R.drawable.ic_menu_send), onClickListener);
        promotedActionsLibrary.addItem(getResources().getDrawable(android.R.drawable.ic_input_get), onClickListener);

        promotedActionsLibrary.addMainItem(getResources().getDrawable(R.drawable.ic_add));
    }
}
