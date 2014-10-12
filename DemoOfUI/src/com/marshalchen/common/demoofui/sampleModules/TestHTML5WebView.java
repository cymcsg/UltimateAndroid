package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import com.marshalchen.common.commonUtils.networkUtils.Html5Webview;

public class TestHTML5WebView extends Activity {
	
	Html5Webview mWebView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = new Html5Webview(this);
        
        if (savedInstanceState != null) {
        	mWebView.restoreState(savedInstanceState);
        } else {
        	mWebView.loadUrl("http://freebsd.csie.nctu.edu.tw/~freedom/html5/");
        	//mWebView.loadUrl("file:///data/bbench/index.html");
        }
        
        setContentView(mWebView.getLayout());
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mWebView.saveState(outState);
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	mWebView.stopLoading();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.inCustomView()) {
            	mWebView.hideCustomView();
            	return true;
            }
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);
    }
}