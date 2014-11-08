package com.marshalchen.common.demoofui.imageprocessingexample;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.imageprocessing.FastImageProcessingPipeline;
import com.marshalchen.common.uimodule.imageprocessing.FastImageProcessingView;
import com.marshalchen.common.uimodule.imageprocessing.GLRenderer;
import com.marshalchen.common.uimodule.imageprocessing.filter.GenericFilter;
import com.marshalchen.common.uimodule.imageprocessing.input.ImageResourceInput;
import com.marshalchen.common.uimodule.imageprocessing.outputs.JPGFileEndpoint;
import com.marshalchen.common.uimodule.imageprocessing.outputs.ScreenEndpoint;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class ImageProcessingGenericFilterExample extends Activity {
	private FastImageProcessingView view;
	private FastImageProcessingPipeline pipeline;
	private ImageResourceInput imageIn;
	private GenericFilter generic;
	private JPGFileEndpoint imageOut;
	private ScreenEndpoint screen;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		view = new FastImageProcessingView(this);
		pipeline = new FastImageProcessingPipeline();
		view.setPipeline(pipeline);
		setContentView(view);
		imageIn = new ImageResourceInput(view, this, R.drawable.image_processing_kukulkan);
		generic = setupGenericFilterAsPolkaDot();
		imageOut = new JPGFileEndpoint(this, true, Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/outputImage", true);
		screen = new ScreenEndpoint(pipeline);
		imageIn.addTarget(generic);
		generic.addTarget(imageOut);
		generic.addTarget(screen);
		pipeline.addRootRenderer(imageIn);
		pipeline.startRendering();
	}
	
	private GenericFilter setupGenericFilterAsPolkaDot() {
		String UNIFORM_FRACTIONAL_WIDTH = "u_FractionalWidth";
		String UNIFORM_ASPECT_RATIO = "u_AspectRatio";
		String UNIFORM_DOT_SCALING = "u_DotScaling";
		GenericFilter filter = new GenericFilter();
		filter.setFragmentShader(
				"precision mediump float;\n" 
				+"uniform sampler2D "+GLRenderer.UNIFORM_TEXTURE0+";\n"  
				+"varying vec2 "+GLRenderer.VARYING_TEXCOORD+";\n"	
				+"uniform float "+UNIFORM_FRACTIONAL_WIDTH+";\n"	
				+"uniform float "+UNIFORM_ASPECT_RATIO+";\n"
				+"uniform highp float "+UNIFORM_DOT_SCALING+";\n"
				
		  		+"void main(){\n"
		  		+"   highp vec2 sampleDivisor = vec2("+UNIFORM_FRACTIONAL_WIDTH+", "+UNIFORM_FRACTIONAL_WIDTH+" /  "+UNIFORM_ASPECT_RATIO+");\n"
			    +"   highp vec2 samplePos = "+GLRenderer.VARYING_TEXCOORD+" - mod("+GLRenderer.VARYING_TEXCOORD+", sampleDivisor) + 0.5 * sampleDivisor;\n"
		  		+"   highp vec2 textureCoordinateToUse = vec2("+GLRenderer.VARYING_TEXCOORD+".x, ("+GLRenderer.VARYING_TEXCOORD+".y * "+UNIFORM_ASPECT_RATIO+" + 0.5 - 0.5 * "+UNIFORM_ASPECT_RATIO+"));\n"
		  		+"   highp vec2 adjustedSamplePos = vec2(samplePos.x, (samplePos.y * "+UNIFORM_ASPECT_RATIO+" + 0.5 - 0.5 * "+UNIFORM_ASPECT_RATIO+"));\n"
			    +"   highp float distanceFromSamplePoint = distance(adjustedSamplePos, textureCoordinateToUse);\n"
			    +"   lowp float checkForPresenceWithinDot = step(distanceFromSamplePoint, ("+UNIFORM_FRACTIONAL_WIDTH+" * 0.5) * "+UNIFORM_DOT_SCALING+");\n"
			    +"   gl_FragColor = vec4(texture2D("+GLRenderer.UNIFORM_TEXTURE0+", samplePos ).rgb * checkForPresenceWithinDot, 1.0);\n"
		  		+"}\n"
				);
		filter.addUniformFloat(UNIFORM_FRACTIONAL_WIDTH, 0.02f);
		filter.addUniformFloat(UNIFORM_DOT_SCALING, 0.9f);
		filter.addUniformFloat(UNIFORM_ASPECT_RATIO, imageIn.getImageWidth()/imageIn.getImageHeight());
		return filter;
	}
}
