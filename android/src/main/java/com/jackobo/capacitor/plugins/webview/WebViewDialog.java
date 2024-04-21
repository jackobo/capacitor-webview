package com.jackobo.capacitor.plugins.webview;

import android.annotation.SuppressLint;
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
    public WebViewDialog(@NonNull Context context, @NonNull OpenWebViewOptions openWebViewOptions) {
        super(context);
        _openWebViewOptions = openWebViewOptions;
    }

    private final OpenWebViewOptions _openWebViewOptions;

    public void showWebViewDialog() {

        setupDialog();

        //LinearLayout rootView = CreateRootLayout();

        WebView webView = createWebView();

        //rootView.addView(webView);

        setContentView(webView);

        webView.loadUrl(_openWebViewOptions.getUrl());


        show();

        webView.requestFocus();
        webView.requestFocusFromTouch();



        _openWebViewOptions.resolvePluginCall();
    }

    private LinearLayout CreateRootLayout() {
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        rootView.setLayoutParams(layoutParams);

        return rootView;
    }
    private void setupDialog() {
        var window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //setCancelable(true);
    }



    @SuppressLint("SetJavaScriptEnabled")
    private WebView createWebView() {

        var webView = new WebView(getContext());
        var webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setDatabaseEnabled(true);
        webViewSettings.setDomStorageEnabled(true);
        webViewSettings.setAllowFileAccess(true);

        webViewSettings.setLoadWithOverviewMode(true);
        webViewSettings.setUseWideViewPort(true);

        webViewSettings.setAllowFileAccessFromFileURLs(true);
        webViewSettings.setAllowUniversalAccessFromFileURLs(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        webView.setLayoutParams(layoutParams);
        return webView;
    }
}
