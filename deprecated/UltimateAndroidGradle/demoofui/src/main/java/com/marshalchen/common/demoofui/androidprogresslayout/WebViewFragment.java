package com.marshalchen.common.demoofui.androidprogresslayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.simplemodule.ProgressLayout;


public class WebViewFragment extends Fragment {
    private static final String TAG = WebViewFragment.class.getSimpleName();

    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_progress_layout_fragment_web_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ProgressLayout progressLayout = (ProgressLayout) view.findViewById(R.id.progress_layout);

        WebView webView = (WebView) view.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressLayout.setProgress(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressLayout.setProgress(false);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.d(TAG, "onReceivedError: " + errorCode + " : " + description + " : " + failingUrl);

                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        webView.loadUrl("http://google.com");
    }
}
