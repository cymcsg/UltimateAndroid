package com.marshalchen.common.demoofui.imageprocessingexample;

import java.util.ArrayList;
import java.util.List;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.imageprocessing.FastImageProcessingPipeline;
import com.marshalchen.common.uimodule.imageprocessing.FastImageProcessingView;
import com.marshalchen.common.uimodule.imageprocessing.filter.BasicFilter;
import com.marshalchen.common.uimodule.imageprocessing.filter.colour.*;
import com.marshalchen.common.uimodule.imageprocessing.filter.effect.*;
import com.marshalchen.common.uimodule.imageprocessing.filter.processing.*;
import com.marshalchen.common.uimodule.imageprocessing.input.CameraPreviewInput;
import com.marshalchen.common.uimodule.imageprocessing.input.GLTextureOutputRenderer;
import com.marshalchen.common.uimodule.imageprocessing.input.ImageResourceInput;
import com.marshalchen.common.uimodule.imageprocessing.outputs.ScreenEndpoint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class ImageProcessingActivity extends Activity {

	private FastImageProcessingView view;
	private List<BasicFilter> filters;
	private int curFilter;
	private GLTextureOutputRenderer input;
	private long touchTime;
	private FastImageProcessingPipeline pipeline;
	private ScreenEndpoint screen;
	private boolean usingCamera;
	
	private void addFilter(BasicFilter filter) {
		filters.add(filter);		
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
		usingCamera = false;
		if (usingCamera) {
			input = new CameraPreviewInput(view);
	    } else {
			input = new ImageResourceInput(view, this, R.drawable.image_processing_kukulkan);
	    }
		filters = new ArrayList<BasicFilter>();
		
		addFilter(new FlipFilter(FlipFilter.FLIP_HORIZONTAL));
		addFilter(new MosaicFilter(this, R.drawable.image_processing_webcircles, new PointF(0.125f, 0.125f), new PointF(0.025f, 0.025f), 64, true));
		addFilter(new CGAColourSpaceFilter());
		addFilter(new KuwaharaRadius3Filter());
		//addFilter(new KuwaharaFilter(8)); //Will not work on devices that don't support for loop on shader
		addFilter(new VignetteFilter(new PointF(0.5f, 0.5f), new float[] {0.3f, 0.8f, 0.3f}, 0.3f, 0.75f)); 
		addFilter(new GlassSphereFilter(new PointF(0.43f, 0.5f), 0.25f, 0.71f, 0.5f));
		addFilter(new SphereRefractionFilter(new PointF(0.43f, 0.5f), 0.25f, 0.71f, 0.5f));
		addFilter(new StretchDistortionFilter(new PointF(0.5f, 0.5f)));
		addFilter(new PinchDistortionFilter(new PointF(0.43f, 0.5f), 0.25f, 0.5f, 0.5f));
		addFilter(new BulgeDistortionFilter(new PointF(0.43f, 0.5f), 0.25f, 0.5f, 0.5f));
		addFilter(new SwirlFilter(new PointF(0.4f, 0.5f), 0.5f, 1f));
		addFilter(new PosterizeFilter(2f));
		addFilter(new EmbossFilter(1.5f));
		addFilter(new SmoothToonFilter(0.25f, 0.5f, 5f));
		addFilter(new ToonFilter(0.4f, 10f));
		addFilter(new ThresholdSketchFilter(0.7f));
		addFilter(new SketchFilter());
		addFilter(new CrosshatchFilter(0.005f, 0.0025f));
		addFilter(new HalftoneFilter(0.01f, 1f));
		addFilter(new PolkaDotFilter(0.9f, 0.03f, 1f));
		addFilter(new PolarPixellateFilter(new PointF(0.4f, 0.5f), new PointF(0.05f, 0.05f)));
		addFilter(new PixellateFilter(0.01f, 1f));
		addFilter(new ZoomBlurFilter(2f, new PointF(0.4f, 0.5f)));
		addFilter(new MotionBlurFilter(2f, 45f));
		addFilter(new OpeningFilter(1));
		addFilter(new OpeningRGBFilter(3));
		addFilter(new ClosingFilter(2));
		addFilter(new ClosingRGBFilter(4));
		addFilter(new ErosionRGBFilter(3));
		addFilter(new ErosionFilter(1));
		addFilter(new DilationRGBFilter(2));
		addFilter(new DilationFilter(4));
		addFilter(new CannyEdgeDetectionFilter(1.0f, 0.1f, 0.4f));
		addFilter(new ThresholdEdgeDetectionFilter(0.6f));
		addFilter(new SobelEdgeDetectionFilter());
		addFilter(new TiltShiftFilter(4f, 0.4f, 0.6f, 0.2f));
		addFilter(new BilateralBlurFilter(1f));
		addFilter(new MedianFilter());
		addFilter(new GaussianBlurPositionFilter(4f, 1.2f, new PointF(0.4f,0.5f), 0.5f, 0.1f));
		addFilter(new GaussianSelectiveBlurFilter(4f, 1.2f, new PointF(0.4f,0.5f), 0.5f, 0.1f));
		addFilter(new SingleComponentGaussianBlurFilter(2.3f));
		addFilter(new SingleComponentFastBlurFilter());
		addFilter(new FastBlurFilter());
		addFilter(new UnsharpMaskFilter(2.0f, 0.5f));
		addFilter(new SharpenFilter(1f));
		//addFilter(new LanczosResamplingFilter(256, 128));
		addFilter(new CropFilter(0.25f,0f,0.75f,1f));
		BasicFilter cFilter1 = new CropFilter(0.25f,0f,0.75f,1f);
		cFilter1.rotateClockwise90Degrees(1);
		addFilter(cFilter1);
		BasicFilter cFilter2 = new CropFilter(0.25f,0f,0.75f,1f);
		cFilter2.rotateClockwise90Degrees(2);
		addFilter(cFilter2);
		BasicFilter cFilter3 = new CropFilter(0.25f,0f,0.75f,1f);
		cFilter3.rotateClockwise90Degrees(3);
		addFilter(cFilter3);
		addFilter(new TransformFilter(new float[] {
				1f, 0f, 0f, 0f,
				0f, 1f, 0f, 0f,
				0f, 0f, 1f, 0f,
				-0.5f, 0f, 0f, 1f
		}, false, false));
		addFilter(new ChromaKeyFilter(new float[] {1.0f, 0.3f, 0.0f}, 0.4f, 0.1f));
		addFilter(new AdaptiveThresholdFilter());
		addFilter(new BoxBlurFilter());
		addFilter(new LuminanceThresholdFilter(0.4f));
		addFilter(new OpacityFilter(0.5f));
		addFilter(new SepiaFilter());
		addFilter(new HazeFilter(0.3f,0.1f));
		addFilter(new FalseColourFilter(new float[]{0.0f,0.0f,0.5f}, new float[]{1.0f,0.0f,0.0f}));
		addFilter(new MonochromeFilter(new float[]{1.0f,0.8f,0.8f}, 1.0f));
		addFilter(new ColourInvertFilter());
		addFilter(new SoftEleganceFilter(this));
		addFilter(new GaussianBlurFilter(2.3f));
		addFilter(new MissEtikateFilter(this));
		addFilter(new AmatorkaFilter(this));
		addFilter(new LookupFilter(this, R.drawable.image_processing_lookup_soft_elegance_1));
		addFilter(new HighlightShadowFilter(0f, 1f));
		Point[] defaultCurve = new Point[] {new Point(128,128), new Point(64,0), new Point(192,255)};
		addFilter(new ToneCurveFilter(defaultCurve,defaultCurve,defaultCurve,defaultCurve));
		addFilter(new HueFilter(3.14f/6f));
		addFilter(new BrightnessFilter(0.5f));
		addFilter(new ColourMatrixFilter(new float[]{	0.33f,0f,0f,0f,
														0f,0.67f,0f,0f,
														0f,0f,1.34f,0f,
														0.2f,0.2f,0.2f,1.0f}, 0.5f));
		addFilter(new RGBFilter(0.33f,0.67f,1.34f));
		addFilter(new GreyScaleFilter());
		addFilter(new ConvolutionFilter(new float[] {
														1/25f,1/25f,1/25f,1/25f,1/25f,
														1/25f,1/25f,1/25f,1/25f,1/25f,
														1/25f,1/25f,1/25f,1/25f,1/25f,
														1/25f,1/25f,1/25f,1/25f,1/25f,
														1/25f,1/25f,1/25f,1/25f,1/25f}, 5, 5));
		addFilter(new ExposureFilter(0.95f));
		addFilter(new ContrastFilter(1.5f));
		addFilter(new SaturationFilter(0.5f));
		addFilter(new GammaFilter(1.75f));
		addFilter(new LevelsFilter(0.2f,0.8f,1f,0f,1f));
		
		screen = new ScreenEndpoint(pipeline);
		
		input.addTarget(screen);
		for(BasicFilter filter : filters) {
			filter.addTarget(screen);
		}
		
		pipeline.addRootRenderer(input);
		pipeline.startRendering();
		final Context context = this;
		view.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent e) {
				if(System.currentTimeMillis() - touchTime > 100) {
					pipeline.pauseRendering();
					touchTime = System.currentTimeMillis();
					if(curFilter == 0) {
						input.removeTarget(screen);
					} else {
						input.removeTarget(filters.get(curFilter-1));
						pipeline.addFilterToDestroy(filters.get(curFilter-1));
					}
					curFilter=(curFilter+1)%(filters.size()+1);
					if(curFilter == 0) {
						input.addTarget(screen);
					} else {
						input.addTarget(filters.get(curFilter-1));
						Toast.makeText(context, filters.get(curFilter-1).getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
					}
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
