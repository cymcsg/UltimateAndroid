package com.marshalchen.common.demoofui.sampleModules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.materialanimatedswitch.MaterialAnimatedSwitch;

public class MaterialAnimatedSwitchActivity extends AppCompatActivity {

  private View mainView;
  private MaterialAnimatedSwitch materialAnimatedSwitch;
  private MaterialAnimatedSwitch materialAnimatedSwitch2;
  private MaterialAnimatedSwitch materialAnimatedSwitch3;
  private MaterialAnimatedSwitch materialAnimatedSwitch4;
  private MaterialAnimatedSwitch masterSwitch;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.material_animated_switch_activity_main);
    mainView = findViewById(R.id.main);
    materialAnimatedSwitch = (MaterialAnimatedSwitch) findViewById(R.id.pin);
    materialAnimatedSwitch2 = (MaterialAnimatedSwitch) findViewById(R.id.pin2);
    materialAnimatedSwitch3 = (MaterialAnimatedSwitch) findViewById(R.id.pin3);
    materialAnimatedSwitch4 = (MaterialAnimatedSwitch) findViewById(R.id.pin4);
    masterSwitch = (MaterialAnimatedSwitch) findViewById(R.id.masterSwitch);

    materialAnimatedSwitch.setOnCheckedChangeListener(
        new MaterialAnimatedSwitch.OnCheckedChangeListener() {
          @Override public void onCheckedChanged(boolean isChecked) {
            Toast.makeText(MaterialAnimatedSwitchActivity.this, materialAnimatedSwitch.isChecked() + "",
                Toast.LENGTH_SHORT).show();
            materialAnimatedSwitch2.toggle();
          }
        });
  }
}
