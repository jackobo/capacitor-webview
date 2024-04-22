package com.jackobo.capacitor.plugins.webview;

import android.content.Intent;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorWebview")
public class CapacitorWebviewPlugin extends Plugin {


    private final IWebViewEvents _eventsCallbacks = new IWebViewEvents() {

        @Override
        public void onUrlChanged(String url) {
            notifyListeners("urlChanged", new JSObject().put("url", url));
        }

        @Override
        public void onWebViewClosed(String url) {
            notifyListeners("webViewClosed", new JSObject().put("url", url));
        }
    };


    @PluginMethod
    public void openWebView(PluginCall call) {
        WebViewActivity.WebViewOptions = new OpenWebViewOptions(call, _eventsCallbacks);
        Intent intent = new Intent(this.getContext(), WebViewActivity.class);
        getContext().startActivity(intent);
    }

    @PluginMethod
    public void closeWebView(PluginCall call) {
        Intent intent = new Intent(WebViewActivity.CLOSE_WEB_VIEW_INTENT);
        getContext().sendBroadcast(intent);
    }

}
