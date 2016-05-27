package com.marshalchen.ua.common.commonUtils.networkUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.marshalchen.ua.R;

/**
 * A webview which support many Html5 feature like video ,audio etc.
 */
public class Html5Webview extends WebView {

    private Context mContext;
    private MyWebChromeClient mWebChromeClient;
    private View mCustomView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    private FrameLayout mContentView;
    private FrameLayout mBrowserFrameLayout;
    private FrameLayout mLayout;

    static final String LOGTAG = "HTML5WebView";

    private void init(Context context) {
        mContext = context;
        Activity mActivity = (Activity) mContext;

        mLayout = new FrameLayout(context);

        mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(mActivity).inflate(R.layout.html5_webview_custom_screen, null);
        mContentView = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.html5_webview_main_content);
        mCustomViewContainer = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.html5_webview_fullscreen_custom_content);

        mLayout.addView(mBrowserFrameLayout, COVER_SCREEN_PARAMS);

        // Configure the webview
        WebSettings s = getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        //  s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        mWebChromeClient = new MyWebChromeClient();
        setWebChromeClient(mWebChromeClient);

        setWebViewClient(new WebViewClient());

        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // enable navigator.geolocation
        // s.setGeolocationEnabled(true);
        // s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");

        // enable Web Storage: localStorage, sessionStorage
        s.setDomStorageEnabled(true);

        mContentView.addView(this);
    }

    public Html5Webview(Context context) {
        super(context);
        init(context);
    }

    public Html5Webview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Html5Webview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public FrameLayout getLayout() {
        return mLayout;
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((mCustomView == null) && canGoBack()) {
                goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            //Log.i(LOGTAG, "here in on ShowCustomView");
            Html5Webview.this.setVisibility(View.GONE);

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
        }

        @Override
        public void onHideCustomView() {

            if (mCustomView == null)
                return;

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            mCustomViewContainer.removeView(mCustomView);
            mCustomView = null;
            mCustomViewContainer.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();

            Html5Webview.this.setVisibility(View.VISIBLE);
            Html5Webview.this.goBack();
            //Log.i(LOGTAG, "set it to webVew");
        }


        @Override
        public View getVideoLoadingProgressView() {
            //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                mVideoProgressView = inflater.inflate(R.layout.html5_webview_video_loading, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            ((Activity) mContext).setTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            ((Activity) mContext).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }
    }


    static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
}
