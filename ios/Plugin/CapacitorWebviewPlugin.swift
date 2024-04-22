import Foundation
import Capacitor

@objc public protocol CapacitorWebViewPluginEvents {
    @objc func notifyUrlChanged(_ url: URL)
    @objc func notifyClosed(_ url: URL?)
}

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorWebviewPlugin)
public class CapacitorWebviewPlugin: CAPPlugin {
    
    private var _webViewController: WebViewController? = nil;
    
    @objc func openWebView(_ call: CAPPluginCall) {
        let options = OpenWebViewOptions(call);
        
        DispatchQueue.main.async {
            if let viewCtrl = self.bridge?.viewController {
                let webViewController = WebViewController(options: options, parentViewController: viewCtrl, pluginEvents: self);
                viewCtrl.present(webViewController, animated: true, completion: nil);
                self._webViewController = webViewController;
                
            } else {
                call.reject("self.bridge?.viewController is null");
            }
        }
        
    }
    
    @objc func closeWebView(_ call: CAPPluginCall) {
        
        DispatchQueue.main.async {
            if let webViewCtrl = self._webViewController {
                webViewCtrl.dismiss(animated: true)
            }
        }
        
        
    }
    
}

extension CapacitorWebviewPlugin: CapacitorWebViewPluginEvents {
    public func notifyUrlChanged(_ url: URL) {
        notifyListeners("urlChanged", data: ["url" : url.absoluteString])
    }
    
    public func notifyClosed(_ url: URL?) {
        notifyListeners("webViewClosed", data: ["url" : url?.absoluteString ?? ""])
    }
    
    
}
