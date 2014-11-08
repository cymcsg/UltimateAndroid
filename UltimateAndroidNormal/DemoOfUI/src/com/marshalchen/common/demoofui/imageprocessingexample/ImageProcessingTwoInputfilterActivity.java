package com.marshalchen.common.demoofui.imageprocessingexample;

import java.util.ArrayList;
import java.util.List;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.imageprocessing.FastImageProcessingPipeline;
import com.marshalchen.common.uimodule.imageprocessing.FastImageProcessingView;
import com.marshalchen.common.uimodule.imageprocessing.filter.MultiInputFilter;
import com.marshalchen.common.uimodule.imageprocessing.filter.blend.*;
import com.marshalchen.common.uimodule.imageprocessing.input.CameraPreviewInput;
import com.marshalchen.common.uimodule.imageprocessing.input.GLTextureOutputRenderer;
import com.marshalchen.common.uimodule.imageprocessing.input.ImageResourceInput;
import com.marshalchen.common.uimodule.imageprocessing.outputs.ScreenEndpoint;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class ImageProcessingTwoInputfilterActivity extends Activity {

    private FastImageProcessingView view;
    private List<MultiInputFilter> filters;
    private int curFilter;
    private GLTextureOutputRenderer input;
    private GLTextureOutputRenderer input2;
    private long touchTime;
    private FastImageProcessingPipeline pipeline;
    private ScreenEndpoint screen;
    private boolean usingCamera;

    private void addFilter(MultiInputFilter filter) {
        filters.add(filter);
        filter.addTarget(screen);
        filter.registerFilterLocation(input);
        filter.registerFilterLocation(input2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = new FastImageProcessingView(this);
        pipeline = new FastImageProcessingPipeline();
        view.setPipeline(pipeline);
        setContentView(view);
        usingCamera = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH);
        if (usingCamera) {
            input = new CameraPreviewInput(view);
        } else {
            input = new ImageResourceInput(view, this, R.drawable.image_processing_kukulkan);
        }
        input2 = new ImageResourceInput(view, this, R.drawable.test_back1);
        filters = new ArrayList<MultiInputFilter>();

        screen = new ScreenEndpoint(pipeline);

        addFilter(new MaskFilter());
        addFilter(new LinearBurnBlendFilter());
        addFilter(new LuminosityBlendFilter()); //TODO fix android 2.2
        addFilter(new SaturationBlendFilter()); //TODO fix android 2.2
        addFilter(new HueBlendFilter());
        addFilter(new ColourBlendFilter()); //TODO fix android 2.2
        addFilter(new NormalBlendFilter());
        addFilter(new SourceOverBlendFilter());
        addFilter(new SoftLightBlendFilter());
        addFilter(new HardLightBlendFilter());
        addFilter(new DifferenceBlendFilter());
        addFilter(new ExclusionBlendFilter());
        addFilter(new ScreenBlendFilter());
        addFilter(new ColourDodgeBlendFilter());
        addFilter(new ColourBurnBlendFilter());
        addFilter(new LightenBlendFilter());
        addFilter(new DarkenBlendFilter());
        addFilter(new OverlayBlendFilter());
        addFilter(new DivideBlendFilter());
        addFilter(new SubtractBlendFilter());
        addFilter(new AddBlendFilter()); //TODO fix
        addFilter(new MultiplyBlendFilter());
        addFilter(new DissolveBlendFilter(0.7f));
        addFilter(new ChromaKeyBlendFilter(new float[]{1.0f, 0.3f, 0.0f}, 0.4f, 0.1f));
        addFilter(new AlphaBlendFilter(0.9f));

        input.addTarget(filters.get(0));
        input2.addTarget(filters.get(0));

        pipeline.addRootRenderer(input);
        pipeline.addRootRenderer(input2);
        pipeline.startRendering();
        final Context context = this;
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent e) {
                if (System.currentTimeMillis() - touchTime > 100) {
                    pipeline.pauseRendering();
                    touchTime = System.currentTimeMillis();
                    input.removeTarget(filters.get(curFilter));
                    input2.removeTarget(filters.get(curFilter));
                    curFilter = (curFilter + 1) % filters.size();
                    input.addTarget(filters.get(curFilter));
                    input2.addTarget(filters.get(curFilter));
                    Toast.makeText(context, filters.get(curFilter).getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
                    pipeline.startRendering();
                    view.requestRender();
                }
                return false;
            }
        });
        Toast.makeText(this, "Tap the screen to change filter.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (usingCamera) {
            ((CameraPreviewInput) input).onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (usingCamera) {
            ((CameraPreviewInput) input).onResume();
        }
    }
}
