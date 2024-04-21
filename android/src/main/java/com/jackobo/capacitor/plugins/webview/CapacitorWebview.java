package com.jackobo.capacitor.plugins.webview;

import android.util.Log;

public class CapacitorWebview {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
