package com.jackobo.capacitor.plugins.webview;
import com.getcapacitor.PluginCall;


public class OpenWebViewOptions {
    OpenWebViewOptions(PluginCall call) {
        this._pluginCall = call;
    }

    private final PluginCall _pluginCall;

    public String getUrl() {
        return _pluginCall.getString("url");
    }

    /*
    public JSObject getHeaders() {
        return _pluginCall.getObject("headers");
    }

    public Boolean getShowAfterPageIsLoaded() {
        return _pluginCall.getBoolean("showAfterPageIsLoaded", true);
    }

    public Boolean getAllowDebug() {
        return _pluginCall.getBoolean("allowDebug", false);
    }

     */

    public void resolveCall() {
        _pluginCall.resolve();
    }

}
