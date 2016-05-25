package com.marshalchen.common.demoofui.imageprocessingexample;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.imageprocessing.FastImageProcessingPipeline;
import com.marshalchen.common.uimodule.imageprocessing.FastImageProcessingView;
import com.marshalchen.common.uimodule.imageprocessing.filter.BasicFilter;
import com.marshalchen.common.uimodule.imageprocessing.filter.processing.SobelEdgeDetectionFilter;
import com.marshalchen.common.uimodule.imageprocessing.input.VideoResourceInput;
import com.marshalchen.common.uimodule.imageprocessing.outputs.JPGFileEndpoint;
import com.marshalchen.common.uimodule.imageprocessing.outputs.ScreenEndpoint;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class ImageProcessingVideotoImageActivity extends Activity {
    private FastImageProcessingView view;
    private BasicFilter edgeDetect;
    private FastImageProcessingPipeline pipeline;
    private VideoResourceInput video;
    private JPGFileEndpoint image;
    private ScreenEndpoint screen;
    private long touchTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = new FastImageProcessingView(this);
        pipeline = new FastImageProcessingPipeline();
        video = new VideoResourceInput(view, this, R.raw.image_processing_birds);
        edgeDetect = new SobelEdgeDetectionFilter();
        image = new JPGFileEndpoint(this, false, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/outputImage", false);
        screen = new ScreenEndpoint(pipeline);
        video.addTarget(edgeDetect);
        edgeDetect.addTarget(image);
        edgeDetect.addTarget(screen);
        pipeline.addRootRenderer(video);
        view.setPipeline(pipeline);
        setContentView(view);
        pipeline.startRendering();
        video.startWhenReady();
        view.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent me) {
                if (System.currentTimeMillis() - 100 > touchTime) {
                    touchTime = System.currentTimeMillis();
                    if (video.isPlaying()) {
                        video.stop();
                    } else {
                        video.startWhenReady();
                    }
                }
                return true;
            }

        });
    }

}
