package com.marshalchen.common.commonUtils.moduleUtils;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.marshalchen.common.commonUtils.logUtils.Logs;

/**
 * A webview which can open itself and add some error info instead of the url
 * User: cym
 * Date: 13-11-4
 *
 */

public  class SampleWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);    //To change body of overridden methods use File | Settings | File Templates.
    }
    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        String errorHtml= "<html><body><h1>网络异常，请检查网络后重试</h1></body></html>";
        Logs.i("-MyWebViewClient->onReceivedError()--\n errorCode=" + errorCode + " \ndescription=" + description + " \nfailingUrl=" + failingUrl);
        //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
       // view.loadData("<html><body><h1>"+"网络异常，请检查网络后重试"+"</h1></body></html>", "text/html", "gbk");
        view.loadDataWithBaseURL("","<html><body><h1>"+"网络异常，请检查网络后重试"+"</h1></body></html>", "text/html", "utf-8", null);

    }

}
