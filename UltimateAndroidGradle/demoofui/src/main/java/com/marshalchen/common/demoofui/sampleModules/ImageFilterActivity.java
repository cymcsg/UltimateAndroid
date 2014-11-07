package com.marshalchen.common.demoofui.sampleModules;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.ImageFilter.Distort.BulgeFilter;
import com.marshalchen.common.uimodule.ImageFilter.Distort.RippleFilter;
import com.marshalchen.common.uimodule.ImageFilter.Distort.TwistFilter;
import com.marshalchen.common.uimodule.ImageFilter.Distort.WaveFilter;
import com.marshalchen.common.uimodule.ImageFilter.IImageFilter;
import com.marshalchen.common.uimodule.ImageFilter.Image;
import com.marshalchen.common.uimodule.ImageFilter.*;
import com.marshalchen.common.uimodule.ImageFilter.Textures.*;

public class ImageFilterActivity extends Activity {

	private ImageView imageView;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_filter_activity);
		
		imageView= (ImageView) findViewById(R.id.imgfilter);
		textView = (TextView) findViewById(R.id.runtime);
	
		Bitmap bitmap = BitmapFactory.decodeResource(ImageFilterActivity.this.getResources(), R.drawable.image_filter_image);
		imageView.setImageBitmap(bitmap);

		LoadImageFilter();
	}

	/**
	 * ����ͼƬfilter
	 */
	private void LoadImageFilter() {
		Gallery gallery = (Gallery) findViewById(R.id.galleryFilter);
		final ImageFilterAdapter filterAdapter = new ImageFilterAdapter(
				ImageFilterActivity.this);
		gallery.setAdapter(new ImageFilterAdapter(ImageFilterActivity.this));
		gallery.setSelection(2);
		gallery.setAnimationDuration(3000);
		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				IImageFilter filter = (IImageFilter) filterAdapter.getItem(position);
				new processImageTask(ImageFilterActivity.this, filter).execute();
			}
		});
	}

	public class processImageTask extends AsyncTask<Void, Void, Bitmap> {
		private IImageFilter filter;
        private Activity activity = null;
		public processImageTask(Activity activity, IImageFilter imageFilter) {
			this.filter = imageFilter;
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			textView.setVisibility(View.VISIBLE);
		}

		public Bitmap doInBackground(Void... params) {
			Image img = null;
			try
	    	{
				Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.image_filter_image);
				img = new Image(bitmap);
				if (filter != null) {
					img = filter.process(img);
					img.copyPixelsFromBuffer();
				}
				return img.getImage();
	    	}
			catch(Exception e){
				if (img != null && img.destImage.isRecycled()) {
					img.destImage.recycle();
					img.destImage = null;
					System.gc(); 
				}
			}
			finally{
				if (img != null && img.image.isRecycled()) {
					img.image.recycle();
					img.image = null;
					System.gc(); 
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if(result != null){
				super.onPostExecute(result);
				imageView.setImageBitmap(result);	
			}
			textView.setVisibility(View.GONE);
		}
	}

	public class ImageFilterAdapter extends BaseAdapter {
		private class FilterInfo {
			public int filterID;
			public IImageFilter filter;

			public FilterInfo(int filterID, IImageFilter filter) {
				this.filterID = filterID;
				this.filter = filter;
			}
		}

		private Context mContext;
		private List<FilterInfo> filterArray = new ArrayList<FilterInfo>();

		public ImageFilterAdapter(Context c) {
			mContext = c;
			
			//99��Ч��
	         
	        //v0.4 
			filterArray.add(new FilterInfo(R.drawable.image_filter_video_filter1, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_STAGGERED)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_video_filter2, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_TRIPED)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_video_filter3, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_3X3)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_video_filter4, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_DOTS)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_tilereflection_filter1, new TileReflectionFilter(20, 8, 45, (byte)1)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_tilereflection_filter2, new TileReflectionFilter(20, 8, 45, (byte)2)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_fillpattern_filter, new FillPatternFilter(ImageFilterActivity.this, R.drawable.image_filter_texture1)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_fillpattern_filter1, new FillPatternFilter(ImageFilterActivity.this, R.drawable.image_filter_texture2)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_mirror_filter1, new MirrorFilter(true)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_mirror_filter2, new MirrorFilter(false)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_ycb_crlinear_filter, new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.3f, 0.3f))));
			filterArray.add(new FilterInfo(R.drawable.image_filter_ycb_crlinear_filter2, new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.276f, 0.163f), new YCBCrLinearFilter.Range(-0.202f, 0.5f))));
			filterArray.add(new FilterInfo(R.drawable.image_filter_texturer_filter, new TexturerFilter(new CloudsTexture(), 0.8f, 0.8f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_texturer_filter1, new TexturerFilter(new LabyrinthTexture(), 0.8f, 0.8f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_texturer_filter2, new TexturerFilter(new MarbleTexture(), 1.8f, 0.8f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_texturer_filter3, new TexturerFilter(new WoodTexture(), 0.8f, 0.8f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_texturer_filter4, new TexturerFilter(new TextileTexture(), 0.8f, 0.8f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter, new HslModifyFilter(20f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter0, new HslModifyFilter(40f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter1, new HslModifyFilter(60f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter2, new HslModifyFilter(80f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter3, new HslModifyFilter(100f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter4, new HslModifyFilter(150f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter5, new HslModifyFilter(200f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter6, new HslModifyFilter(250f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_hslmodify_filter7, new HslModifyFilter(300f)));
			
			//v0.3  
			filterArray.add(new FilterInfo(R.drawable.image_filter_zoomblur_filter, new ZoomBlurFilter(30)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_threedgrid_filter, new ThreeDGridFilter(16, 100)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_colortone_filter, new ColorToneFilter(Color.rgb(33, 168, 254), 192)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_colortone_filter2, new ColorToneFilter(0x00FF00, 192)));//green
			filterArray.add(new FilterInfo(R.drawable.image_filter_colortone_filter3, new ColorToneFilter(0xFF0000, 192)));//blue
			filterArray.add(new FilterInfo(R.drawable.image_filter_colortone_filter4, new ColorToneFilter(0x00FFFF, 192)));//yellow
			filterArray.add(new FilterInfo(R.drawable.image_filter_softglow_filter, new SoftGlowFilter(10, 0.1f, 0.1f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_tilereflection_filter, new TileReflectionFilter(20, 8)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_blind_filter1, new BlindFilter(true, 96, 100, 0xffffff)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_blind_filter2, new BlindFilter(false, 96, 100, 0x000000)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_raiseframe_filter, new RaiseFrameFilter(20)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_shift_filter, new ShiftFilter(10)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_wave_filter, new WaveFilter(25, 10)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_bulge_filter, new BulgeFilter(-97)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_twist_filter, new TwistFilter(27, 106)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_ripple_filter, new RippleFilter(38, 15, true)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_illusion_filter, new IllusionFilter(3)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_supernova_filter, new SupernovaFilter(0x00FFFF,20,100)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_lensflare_filter, new LensFlareFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_posterize_filter, new PosterizeFilter(2)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_gamma_filter, new GammaFilter(50)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_sharp_filter, new SharpFilter()));
			
			//v0.2
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new ComicFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new SceneFilter(5f, Gradient.Scene())));//green
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new SceneFilter(5f, Gradient.Scene1())));//purple
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new SceneFilter(5f, Gradient.Scene2())));//blue
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new SceneFilter(5f, Gradient.Scene3())));
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new FilmFilter(80f)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new FocusFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new CleanGlassFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new PaintBorderFilter(0x00FF00)));//green
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new PaintBorderFilter(0x00FFFF)));//yellow
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new PaintBorderFilter(0xFF0000)));//blue
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new LomoFilter()));
			
			//v0.1
			filterArray.add(new FilterInfo(R.drawable.image_filter_invert_filter, new InvertFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_blackwhite_filter, new BlackWhiteFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_edge_filter, new EdgeFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_pixelate_filter, new PixelateFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_neon_filter, new NeonFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_bigbrother_filter, new BigBrotherFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_monitor_filter, new MonitorFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_relief_filter, new ReliefFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_brightcontrast_filter,new BrightContrastFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_saturationmodity_filter,	new SaturationModifyFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_threshold_filter,	new ThresholdFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_noisefilter,	new NoiseFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_banner_filter1, new BannerFilter(10, true)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_banner_filter2, new BannerFilter(10, false)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_rectmatrix_filter, new RectMatrixFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_blockprint_filter, new BlockPrintFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_brick_filter,	new BrickFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_gaussianblur_filter,	new GaussianBlurFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_light_filter,	new LightFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_mosaic_filter,new MistFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_mosaic_filter,new MosaicFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_oilpaint_filter,	new OilPaintFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_radialdistortion_filter,new RadialDistortionFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_reflection1_filter,new ReflectionFilter(true)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_reflection2_filter,new ReflectionFilter(false)));
			filterArray.add(new FilterInfo(R.drawable.image_filter_saturationmodify_filter,	new SaturationModifyFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_smashcolor_filter,new SmashColorFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_tint_filter,	new TintFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_vignette_filter,	new VignetteFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_autoadjust_filter,new AutoAdjustFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_colorquantize_filter,	new ColorQuantizeFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_waterwave_filter,	new WaterWaveFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_vintage_filter,new VintageFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_oldphoto_filter,new OldPhotoFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_sepia_filter,	new SepiaFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_rainbow_filter,new RainBowFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_feather_filter,new FeatherFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_xradiation_filter,new XRadiationFilter()));
			filterArray.add(new FilterInfo(R.drawable.image_filter_nightvision_filter,new NightVisionFilter()));

			filterArray.add(new FilterInfo(R.drawable.image_filter_saturationmodity_filter,null/* �˴������ԭͼЧ�� */));
		}

		public int getCount() {
			return filterArray.size();
		}

		public Object getItem(int position) {
			return position < filterArray.size() ? filterArray.get(position).filter
					: null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Bitmap bmImg = BitmapFactory
					.decodeResource(mContext.getResources(),
							filterArray.get(position).filterID);
			int width = 100;// bmImg.getWidth();
			int height = 100;// bmImg.getHeight();
			bmImg.recycle();
			ImageView imageview = new ImageView(mContext);
			imageview.setImageResource(filterArray.get(position).filterID);
			imageview.setLayoutParams(new Gallery.LayoutParams(width, height));
			imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);// ������ʾ��������
			return imageview;
		}
	};

}
