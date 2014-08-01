package com.fss.common.demo.showcaseview;

import android.app.Activity;
import android.os.Bundle;
import com.fss.common.uiModule.showcaseview.ShowcaseView;
import com.fss.common.uiModule.showcaseview.targets.Target;
import com.fss.common.uiModule.showcaseview.targets.ViewTarget;
import com.fss.common.demo.R;


public class SingleShotActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcase_activity_single_shot);

        Target viewTarget = new ViewTarget(R.id.button, this);
        new ShowcaseView.Builder(this, true)
                .setTarget(viewTarget)
                .setContentTitle("title_single_shot")
                .setContentText("_string_desc_single_shot")
                .singleShot(42)
                .build();
    }
}
