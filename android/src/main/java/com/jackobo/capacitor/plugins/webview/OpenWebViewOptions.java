package com.jackobo.capacitor.plugins.webview;
import androidx.annotation.Nullable;

import com.getcapacitor.PluginCall;




public class OpenWebViewOptions {
    OpenWebViewOptions(PluginCall call, IWebViewEvents events) {
        this._pluginCall = call;
        this._events = events;
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

    public void resolvePluginCall() {
        _pluginCall.resolve();
    }

}
