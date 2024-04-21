package com.jackobo.capacitor.plugins.webview;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorWebview")
public class CapacitorWebviewPlugin extends Plugin {

    private CapacitorWebview implementation = new CapacitorWebview();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    private WebViewDialog _webViewDialog = null;
    @PluginMethod
    public void openWebView(PluginCall call) {
        OpenWebViewOptions options = new OpenWebViewOptions(call);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _webViewDialog = new WebViewDialog(getContext(), options);
                _webViewDialog.showWebViewDialog();
            }
        });
    }
}
