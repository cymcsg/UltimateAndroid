package com.marshalchen.common.demoofui.edgeeffectoverride;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.marshalchen.common.demoofui.R;

public class WebViewActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edge_effect_webview_layout);

        mWebView = ((WebView) findViewById(R.id.webview));
        initWebView();
    }

    private void initWebView() {
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = mWebView.getSettings();
        settings.setSavePassword(true);
        settings.setSaveFormData(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(false);


        mWebView.loadUrl("http://developer.android.com");
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressed();
    }
}
