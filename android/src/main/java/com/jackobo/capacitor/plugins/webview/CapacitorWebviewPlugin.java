package com.jackobo.capacitor.plugins.webview;

import android.content.Intent;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorWebview")
public class CapacitorWebviewPlugin extends Plugin {


    private IWebViewEvents _eventsCallbacks = new IWebViewEvents() {

        @Override
        public void onUrlChanged(String url) {
            notifyListeners("urlChangedEvent", new JSObject().put("url", url));
        }

        @Override
        public void onWebViewClosed(String url) {
            notifyListeners("webViewClosedEvent", new JSObject().put("url", url));
        }
    };


    @PluginMethod
    public void openWebView(PluginCall call) {
        OpenWebViewOptions options = new OpenWebViewOptions(call, _eventsCallbacks);
        WebViewActivity.WebViewOptions = options;
        Intent intent = new Intent(this.getContext(), WebViewActivity.class);
        getContext().startActivity(intent);
    }

}
