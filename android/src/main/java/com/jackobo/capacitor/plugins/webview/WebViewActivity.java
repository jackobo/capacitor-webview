package com.jackobo.capacitor.plugins.webview;

import android.annotation.SuppressLint;
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
import androidx.core.graphics.drawable.DrawableCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.webViewActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupActionBar(WebViewOptions.getToolbarOptions());


        WebView webView = setupWebView(WebViewOptions);;


        webView.loadUrl(WebViewOptions.getUrl());
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