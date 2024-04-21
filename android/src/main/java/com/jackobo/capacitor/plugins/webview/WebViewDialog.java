package com.jackobo.capacitor.plugins.webview;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;


public class WebViewDialog extends Dialog {
    public WebViewDialog(@NonNull Context context, OpenWebViewOptions openWebViewOptions) {
        super(context);
        _openWebViewOptions = openWebViewOptions;
    }

    private final OpenWebViewOptions _openWebViewOptions;
    private WebView _webView;

    public void showWebViewDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);

        var window = getWindow();
        if(window != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }


        _webView = new WebView(getContext());
        var webViewSettings = _webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setDatabaseEnabled(true);
        webViewSettings.setDomStorageEnabled(true);
        webViewSettings.setAllowFileAccess(true);
        //webViewSettings.setLoadWithOverviewMode(true);
        //webViewSettings.setUseWideViewPort(true);
        webViewSettings.setAllowFileAccessFromFileURLs(true);
        webViewSettings.setAllowUniversalAccessFromFileURLs(true);

        _webView.setWebViewClient(new WebViewClient());
        _webView.setWebChromeClient(new WebChromeClient());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        _webView.setLayoutParams(layoutParams);

        _webView.loadUrl(_openWebViewOptions.getUrl());
        _webView.requestFocus();
        _webView.requestFocusFromTouch();

        setContentView(_webView);

        show();

        _openWebViewOptions.resolveCall();
    }

}
