package com.jackobo.capacitor.plugins.webview;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


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

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);

        setupWindow();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.webViewActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupActionBar(WebViewOptions.getToolbarOptions());


        WebView webView = setupWebView(WebViewOptions);


        webView.loadUrl(WebViewOptions.getUrl());
    }

    @Override
    protected void onStart() {
        super.onStart();
        var intentFilter = new IntentFilter(CLOSE_WEB_VIEW_INTENT);
        registerReceiver(_broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(_broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        WebView webView = findViewById(R.id.pluginWebView);
        WebViewOptions.getEvents().onWebViewClosed(webView.getUrl());
        super.onDestroy();
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

        var backgroundColor = Color.parseColor(options.getBackgroundColor());
        actionBar.setBackgroundDrawable(new ColorDrawable(backgroundColor));


        var htmlTitle = String.format("<font color='%s'>%s</font>", options.getColor(), options.getTitle());
        actionBar.setTitle(Html.fromHtml(htmlTitle));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_webview_back_arrow);
        if(drawable != null) {
            drawable.setColorFilter(Color.parseColor(options.getColor()), PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(drawable);
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

        webViewSettings.setAllowFileAccessFromFileURLs(true);
        webViewSettings.setAllowUniversalAccessFromFileURLs(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(!_initialPageLoadOccurred) {
                    _initialPageLoadOccurred = true;
                    webView.clearHistory();
                    options.resolvePluginCall();
                }
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                if(!isReload) {
                    options.getEvents().onUrlChanged(url);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        return webView;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}