UltimateAndroid Quick Setup
=====
[中文教程](https://github.com/cymcsg/UltimateAndroid/blob/master/Manual_Chinese.md)

[ReadMe](https://github.com/cymcsg/UltimateAndroid)

###Info


1.The core function of UltimateAndroid Framework contains  View Injection,ORM,Asynchronous Http and Image etc.

2.UltimateAndroid  use a lot terrific opensource project.


###Quick Setup（Basic Usage）
1.If this is the first time for you to use the framework, you can use CommonApplication as your Application of Android app or just let your custom application extends CommonApplication.

2. As the function of View Injection which use ButterKnife,you should config your IDE before you can compile the project.***Most of  IDEs require additional configuration in order to enable annotation processing for Butter Knife,
or you can see [IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].***

3.View Injection:
  
  ``Example:``
  
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
	
4.Asynchronous Network:
  Use asynchronous utils,you do not need to use an addtional Thread to visit network.

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
	
5.Display Images:
  If you have already use or extend CommonApplication,you can use like this:
  ```ImageLoader.getInstance().displayImage((imageUri, imageView));```
  
  Or for some advantage usage:


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
	
	
Acceptable URIs examples:

	String imageUri = "http://site.com/image.png"; // from Web
	String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
	String imageUri = "content://media/external/audio/albumart/13"; // from content provider
	String imageUri = "assets://image.png"; // from assets
	String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, 	non-9patch)
	
**NOTE**: Use drawable:// only if you really need it! Always consider the native way to load drawables - ImageView.setImageResource(...) instead of using of ImageLoader.


6.ORM:

 The Orm Module of the framework contains both [GreenDao](https://github.com/greenrobot/greenDAO) and [ActiveRecord](https://github.com/pardom/ActiveAndroid).
 
 You can choose either of them freely.						
	

  
  
  
[101]:http://jakewharton.github.io/butterknife/ide-idea.html
[102]:http://jakewharton.github.io/butterknife/ide-eclipse.html

======
