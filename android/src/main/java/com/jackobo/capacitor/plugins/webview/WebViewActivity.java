package com.jackobo.capacitor.plugins.webview;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;


public class WebViewActivity extends AppCompatActivity {

    public static OpenWebViewOptions WebViewOptions;
    public static final String CLOSE_WEB_VIEW_INTENT = "CLOSE_WEB_VEW_ACTIVITY";
    private boolean _initialPageLoadOccurred = false;

    private final BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            var action = intent.getAction();
            if(action != null && action.equals(CLOSE_WEB_VIEW_INTENT)) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var options = WebViewOptions;

        var screenOrientation = options.getScreenOrientation();
        if(screenOrientation != null) {
            setRequestedOrientation(screenOrientation);
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);

        setupWindow();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.webViewActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupActionBar(options.getToolbarOptions());

        var webView = setupWebView(options);

        webView.loadUrl(options.getUrl(), options.getHeaders());
    }

    @Override
    protected void onStart() {
        super.onStart();
        var intentFilter = new IntentFilter(CLOSE_WEB_VIEW_INTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(_broadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(_broadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(_broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        WebView webView = findViewById(R.id.pluginWebView);
        var url = webView.getUrl();
        super.onDestroy();
        WebViewOptions.resolvePluginCall(url);
        WebViewOptions.getEvents().onWebViewClosed(url);
    }

    private void setupWindow() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setupActionBar(@Nullable WebViewToolbarOptions options) {
        var actionBar = getSupportActionBar();
        if(actionBar == null) {
            return;
        }

        if(options == null) {
            actionBar.hide();
            return;
        }

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        var backgroundColor = new ColorDrawable(Color.parseColor(options.getBackgroundColor()));
        actionBar.setBackgroundDrawable(backgroundColor);

        actionBar.setCustomView(R.layout.webview_actionbar_content);
        actionBar.setDisplayShowCustomEnabled(true);

        TextView titleTextView = findViewById(R.id.webviewTitleText);
        titleTextView.setText(options.getTitle());
        titleTextView.setTextColor(Color.parseColor(options.getColor()));

        Button closeButton = findViewById(R.id.webViewCloseXButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_webview_x_close);
        if(drawable != null) {
            drawable.setColorFilter(Color.parseColor(options.getColor()), PorterDuff.Mode.SRC_ATOP);
            closeButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private WebView setupWebView(OpenWebViewOptions options) {

        WebView webView = findViewById(R.id.pluginWebView);

        var webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setDatabaseEnabled(true);
        webViewSettings.setDomStorageEnabled(true);
        webViewSettings.setAllowFileAccess(true);

        webViewSettings.setLoadWithOverviewMode(true);
        webViewSettings.setUseWideViewPort(true);

        webViewSettings.setBuiltInZoomControls(true);
        webViewSettings.setDisplayZoomControls(false);

        webViewSettings.setAllowFileAccessFromFileURLs(true);
        webViewSettings.setAllowUniversalAccessFromFileURLs(true);
        if(!TextUtils.isEmpty(options.getUserAgent())) {
            webViewSettings.setUserAgentString(options.getUserAgent());
        }


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(!_initialPageLoadOccurred) {
                    _initialPageLoadOccurred = true;
                    var spinner = findViewById(R.id.webViewLoadingProgress);
                    spinner.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                    webView.clearHistory();
                }
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                if(!isReload) {
                    options.getEvents().onUrlChanged(url);
                }
            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if(options.getIgnoreSslErrors() && error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                    handler.proceed();
                } else {
                    super.onReceivedSslError(view, handler, error);
                }
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                try {
                    var requestedUrl = request.getUrl().toString().toLowerCase();
                    Log.i("shouldOverrideUrlLoading", requestedUrl);
                    if(request.isRedirect()) {
                        return false;
                    }

                    var urlsToOpenInExternalBrowser = WebViewOptions.getUrlPatternsToOpenInExternalBrowser();

                    for(var i = 0; i < urlsToOpenInExternalBrowser.length; i++) {
                        if(requestedUrl.contains(urlsToOpenInExternalBrowser[i])) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                            view.getContext().startActivity(intent);
                            return true;
                        }
                    }

                    return false;
                } catch (Exception ex) {
                    Log.e("shouldOverrideUrlLoading", ex.toString());
                    return false;
                }

            }


        });
        webView.setWebChromeClient(new WebChromeClient());

        return webView;
    }

}