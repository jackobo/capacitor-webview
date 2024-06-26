//
//  OpenWebViewOptions.swift
//  Plugin
//
//  Created by Florin Iacob on 22.04.2024.
//  Copyright © 2024 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor

@objc public class WebViewToolbarOptions: NSObject {
    public init(_ jsObject: JSObject) {
        self._jsObject = jsObject;
    }
    
    private let _jsObject: JSObject;
    
    private func getStringProp(_ propName: String) -> String {
        guard let propValue = _jsObject[propName] as? String else {
            return ""
        }
        return propValue
    }
    
    
    var title: String {
        get {
            return getStringProp("title")
            
        }
    }
    
    var backgroundColor: String {
        get {
            return getStringProp("backgroundColor")
        }
    }
    
    var color: String {
        get {
            return getStringProp("color")
        }
    }
    
}

@objc public class OpenWebViewOptions: NSObject {
    public init(_ pluginCall: CAPPluginCall) {
        self._pluginCall = pluginCall
    }
    
    private let _pluginCall: CAPPluginCall;
    
    var url: String {
        get {
            return _pluginCall.getString("url", "")
        }
    }
    
    var toolbar: WebViewToolbarOptions? {
        get {
            if let jsOptions = _pluginCall.getObject("toolbar") {
                return WebViewToolbarOptions(jsOptions)
            } else {
                return nil
            }
                
        }
    }
    
    var enableDebug: Bool {
        get {
            return self._pluginCall.getBool("enableDebug", false)
        }
    }
    
    @objc private func jsHeadersToDictionary() -> [String: String] {
        guard let jsHeaders = _pluginCall.getObject("headers") else {
            return [:];
        }
        
        var headers: [String: String] = [:]
        
        for (key, value) in jsHeaders {
            if let val = value as? String {
                headers[key] = val
            }
        }
        
        return headers;
    }
    
    private let USER_AGENT_HEADER_NAME = "user-agent"
    
    var userAgent: String? {
        get {
            
            for (key, value) in self.jsHeadersToDictionary() {
                if(key.lowercased() == USER_AGENT_HEADER_NAME) {
                    return value;
                }
            }
            
            return nil
        }
    }
    
    var headers: [String: String] {
        get {
            
            var result: [String: String] = [:]
            
            for (key, value) in self.jsHeadersToDictionary() {
                if(key.lowercased() != USER_AGENT_HEADER_NAME) {
                    result[key] = value
                }
            }
            
            return result;
        }
    }
   
   
    
    @objc func resolveCall(_ url: URL?) {
        
        self._pluginCall.resolve([
            "url": url?.absoluteString ?? ""
        ])
        
    }
    
    
    
    
}

