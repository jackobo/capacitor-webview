package com.jackobo.capacitor.plugins.webview;

import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class OpenWebViewOptions {
    OpenWebViewOptions(PluginCall call, IWebViewEvents events) {
        this._pluginCall = call;
        this._events = events;
        loadHeaders();
    }

    private final PluginCall _pluginCall;
    private final IWebViewEvents _events;

    public IWebViewEvents getEvents() {
        return this._events;
    }

    public String getUrl() {
        return _pluginCall.getString("url");
    }

    @Nullable
    public WebViewToolbarOptions getToolbarOptions() {
        var jsOptions = _pluginCall.getObject("toolbar", null);
        if(jsOptions == null) {
            return null;
        }

        return new WebViewToolbarOptions(jsOptions);
    }




    private void loadHeaders() {

        var jsHeaders = _pluginCall.getObject("headers");

        if(jsHeaders == null) {
            return;
        }

        Iterator<String> keys = jsHeaders.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            if (key.equalsIgnoreCase("user-agent")) {
                _userAgent = jsHeaders.getString(key);
            } else {
                _headers.put(key, jsHeaders.getString(key));
            }
        }

    }

    private String _userAgent = null;

    @Nullable
    public String getUserAgent() {
        return _userAgent;
    }

    private final HashMap<String, String> _headers = new HashMap<>();
    public Map<String, String> getHeaders() {
        return _headers;
    }

    public Boolean getIgnoreSslErrors() {
        return _pluginCall.getBoolean("ignoreSslErrors", false);
    }

    public void resolvePluginCall(String url) {
        JSObject ret = new JSObject();
        ret.put("url", url);
        _pluginCall.resolve(ret);
    }

}
