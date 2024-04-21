package com.jackobo.capacitor.plugins.webview;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;


public class OpenWebViewOptions {
    OpenWebViewOptions(PluginCall call) {
        this._pluginCall = call;
    }

    private PluginCall _pluginCall;

    public String getUrl() {
        return _pluginCall.getString("url");
    }

    public JSObject getHeaders() {
        return _pluginCall.getObject("headers");
    }

    public Boolean getShowAfterPageIsLoaded() {
        return _pluginCall.getBoolean("showAfterPageIsLoaded", true);
    }

    public Boolean getAllowDebug() {
        return _pluginCall.getBoolean("allowDebug", false);
    }

}
