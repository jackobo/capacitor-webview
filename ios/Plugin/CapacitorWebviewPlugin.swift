import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorWebviewPlugin)
public class CapacitorWebviewPlugin: CAPPlugin {
    
    @objc func openWebView(_ call: CAPPluginCall) {
        let options = OpenWebViewOptions(call);
        
        
        DispatchQueue.main.async {
            if let viewCtrl = self.bridge?.viewController {
                let webViewController = WebViewController(options: options, parentViewController: viewCtrl);
                viewCtrl.present(webViewController, animated: true, completion: nil);
                call.resolve();
            } else {
                call.reject("self.bridge?.viewController is null");
            }
                    
        }
        
    }
    
}
