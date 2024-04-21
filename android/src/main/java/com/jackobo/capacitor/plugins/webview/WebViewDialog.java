package com.jackobo.capacitor.plugins.webview;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.getcapacitor.plugin.WebView;

public class WebViewDialog extends Dialog {
    public WebViewDialog(@NonNull Context context, OpenWebViewOptions openWebViewOptions) {
        super(context);
        _openWebViewOptions = openWebViewOptions;
    }

    private OpenWebViewOptions _openWebViewOptions;
    private WebView _webView;

    public void showWebViewDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setContentView(R.layout.activity_browser);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

    }

}
