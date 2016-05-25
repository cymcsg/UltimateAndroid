package com.marshalchen.common.demoofui.parallaxswipeback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.ParallaxSwipeBackActivity;

/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}(用一句话描述该文件做什么)
 * Created by bushijie33@gmail.com on 2015/7/5.
 */
public class ParallaxSwipeBackNextActivity extends ParallaxSwipeBackActivity {
    private int[] images = new int[]{R.drawable.test, R.drawable.test_back, R.drawable.test_back1, R.drawable.test_back2};
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.parallax_swipe_back_activity_next);
        index = getIntent().getIntExtra("index",0);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(images[index]);
        TextView textView = (TextView) findViewById(R.id.next);
        textView.setText(textView.getText().toString()+index);

        final Intent intent = new Intent(this,ParallaxSwipeBackNextActivity.class);
        intent.putExtra("index",++index);
        if(index==images.length){
            textView.setText("FINISH ACTIVITY");
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==images.length){
                    finish();
                    ParallaxSwipeBackNextActivity.this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                }else
                startParallaxSwipeBackActivty(ParallaxSwipeBackNextActivity.this,intent);
            }
        });

    }
}
