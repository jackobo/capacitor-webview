package com.jackobo.capacitor.plugins.webview;

public interface IWebViewEvents {
    public void onUrlChanged(String url);
    public void onWebViewClosed(String url);
}
