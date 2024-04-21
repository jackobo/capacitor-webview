package com.jackobo.capacitor.plugins.webview;

import com.getcapacitor.JSObject;

public class WebViewToolbarOptions {
    WebViewToolbarOptions(JSObject toolbarOptions) {
        _toolbarOptions = toolbarOptions;
    }

    private final JSObject _toolbarOptions;

    public String getTitle() {
        return _toolbarOptions.getString("title");
    }

    public String getBackgroundColor() {
        return _toolbarOptions.getString("backgroundColor");
    }

    public String getColor() {
        return _toolbarOptions.getString("color");
    }
}