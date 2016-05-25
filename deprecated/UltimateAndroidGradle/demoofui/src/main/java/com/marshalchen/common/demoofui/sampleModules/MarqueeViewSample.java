package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.MarqueeView;

import java.util.Random;


public class MarqueeViewSample extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marquee_view_activity);

        // Marquee #1: Configuration using code.
        final MarqueeView mv = (MarqueeView) findViewById(R.id.marqueeView100);
        mv.setPauseBetweenAnimations(500);
        mv.setSpeed(10);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mv.startMarquee();
            }
        });

        // Marquee #2: Configured via XML.
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        findViewById(R.id.btnChangeText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] strings = new String[] {
                        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed.",
                        "do eiusmod tempor incididunt",
                        "fugiat nulla pariatur. Excepteur sint occaecat cupidatat",
                        "sunt in culpa qui officia",
                        "nisi ut aliquid",
                        "aliquid ex ea commodi consequatur",
                        "inventore veritatis et quasi architecto",
                        "beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem"
                };
                textView2.setText(strings[Math.abs(new Random().nextInt() % strings.length)]);
            }
        });

        // Marquee #3: Manual Start/Stop
        final MarqueeView marqueeView3 = (MarqueeView) findViewById(R.id.marqueeView3);
        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marqueeView3.startMarquee();
            }
        });
        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marqueeView3.reset();
            }
        });
    }
}
