CommonLibsForAndroid 使用手册
=====
[English Version]()

[ReadMe]()


###简介
1.框架核心模块包括视图注入，ORM，异步的Http与图像请求等内容。
2.CommonLibsForAndroid 使用了许多优秀的开源框架
3.目前的项目结构还未采用Gradle，可能在未来某一时间会采用

###快速入门（基础使用）
1.如果这是您第一次使用CommonLibsForAndroid，你可以使用CommonApplication作为Android App的Application或者使用自定义的Application继承CommonApplication。

2.由于框架使用了View Injection，**大部分IDE需要开启annotation的编译（使用了Butter Knife），如果不清楚如何开启可以看一下[IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].**

3.视图注入：
 ```Example:```
  
 	class ExampleActivity extends Activity {
  	@InjectView(R.id.title) TextView title;
  	@InjectView(R.id.subtitle) TextView subtitle;
  	@InjectView(R.id.footer) TextView footer;

  	@Override public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.simple_activity);
    	ButterKnife.inject(this);
    	// TODO Use "injected" views...
  }
}

Another use is simplifying the view holder pattern inside of a list adapter.

	public class MyAdapter extends BaseAdapter {
 	  @Override public View getView(int position, View view, ViewGroup parent) {
    	ViewHolder holder;
    	if (view != null) {
      	holder = (ViewHolder) view.getTag();
    	} else {
      	view = inflater.inflate(R.layout.whatever, parent, false);
      	holder = new ViewHolder(view);
      	view.setTag(holder);
    	}
    	holder.name.setText("John Doe");
    	// etc...

  	  return view;
  	}

  	static class ViewHolder {
   	  @InjectView(R.id.title) TextView name;
   	  @InjectView(R.id.job_title) TextView jobTitle;
	
   	  public ViewHolder(View view) {
   	    ButterKnife.inject(this, view);
      }
  	}
	}


4.异步网络请求:
  使用异步网络请求工具，你不需要在额外的声明Thread来进行网络请求。

	HttpUtilsAsync.get("http://www.google.com", new AsyncHttpResponseHandler() {

    @Override
    public void onStart() {
        // called before request is started
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
        // called when response HTTP status is "200 OK"
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
    }

    @Override
    public void onRetry(int retryNo) {
        // called when request is retried
	}
	});
	
Post request:
	
	HttpUtilsAsync.post("http://www.google.com", new AsyncHttpResponseHandler() {

    @Override
    public void onStart() {
        // called before request is started
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
        // called when response HTTP status is "200 OK"
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
    }

    @Override
    public void onRetry(int retryNo) {
        // called when request is retried
	}
	});
	
5.显示图片:
  如果你已经使用或继承了CommonApplication，你可以如下使用:
  ```ImageLoader.getInstance().displayImage((imageUri, imageView));```
  
  或者高级使用:
  
  	// Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view 
	//  which implements ImageAware interface)
	imageLoader.displayImage(imageUri, imageView, displayOptions, new ImageLoadingListener() 	{
	    @Override
    	public void onLoadingStarted(String imageUri, View view) {
        	...
    	}
    	@Override
    	public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
	        ...
	    }
 	   @Override
 	   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
 	       ...
 	   }
 	   @Override
  	  public void onLoadingCancelled(String imageUri, View view) {
  	      ...
  	  }
	}, new ImageLoadingProgressListener() {
  	  @Override
  	  public void onProgressUpdate(String imageUri, View view, int current, int total) {
  	      ...
  	  }
	});
	
<br>

	// Load image, decode it to Bitmap and return Bitmap to callback
	ImageSize targetSize = new ImageSize(120, 80); // result Bitmap will be fit to this size
	imageLoader.loadImage(imageUri, targetSize, displayOptions, new 	SimpleImageLoadingListener() {
    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        // Do whatever you want with Bitmap
    }
	});	
	
	
可以使用的URI格式:

	String imageUri = "http://site.com/image.png"; // from Web
	String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
	String imageUri = "content://media/external/audio/albumart/13"; // from content provider
	String imageUri = "assets://image.png"; // from assets
	String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, 	non-9patch)
	
**NOTE**: Use drawable:// only if you really need it! Always consider the native way to load drawables - ImageView.setImageResource(...) instead of using of ImageLoader.


6.ORM:

 Orm模块包括[GreenDao](https://github.com/greenrobot/greenDAO) and [ActiveRecord](https://github.com/pardom/ActiveAndroid).
 
 你可以自由选择两者中的一个。					



[101]:http://jakewharton.github.io/butterknife/ide-idea.html
[102]:http://jakewharton.github.io/butterknife/ide-eclipse.html