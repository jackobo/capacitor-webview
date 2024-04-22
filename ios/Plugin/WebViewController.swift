//
//  WebViewController.swift
//  Plugin
//
//  Created by Florin Iacob on 22.04.2024.
//  Copyright © 2024 Max Lynch. All rights reserved.
//

import Foundation
import UIKit
import WebKit

@objc
public class WebViewController: UIViewController {
    
    private let _options: OpenWebViewOptions;
    private var _webView: WKWebView? = nil;
    private var _activityIndicatorView: UIActivityIndicatorView?
    private let _pluginEvents: CapacitorWebViewPluginEvents
    private var _isInitialPageLoad: Bool = true
    
    
    init(options: OpenWebViewOptions, parentViewController: UIViewController, pluginEvents: CapacitorWebViewPluginEvents) {
       
        self._options = options;
        self._pluginEvents = pluginEvents;
        super.init(nibName: nil, bundle: nil);
        
        var toolbar: UIToolbar? = nil
        if let toolBarOptions = options.toolbar {
            toolbar = setupToolbar(toolBarOptions);
            
        }
        
        let spinnerView = UIActivityIndicatorView(style: .medium)
        spinnerView.hidesWhenStopped = true
        spinnerView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(spinnerView)
        spinnerView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        spinnerView.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
        self._activityIndicatorView = spinnerView
                    
        
        
        if let url = URL(string: options.url) {
            let webView = WKWebView(frame: UIScreen.main.bounds);
            view.addSubview(webView)
            webView.translatesAutoresizingMaskIntoConstraints = false
            
            if let toolbar = toolbar {
                NSLayoutConstraint.activate([
                    webView.topAnchor.constraint(equalTo: toolbar.bottomAnchor),
                    webView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
                    webView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
                    webView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
                ])
            } else {
                let guide = view.safeAreaLayoutGuide
                NSLayoutConstraint.activate([
                    webView.topAnchor.constraint(equalTo: guide.topAnchor),
                    webView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
                    webView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
                    webView.bottomAnchor.constraint(equalTo: guide.bottomAnchor),
                ])
                
            }
            
            webView.addObserver(self, forKeyPath: #keyPath(WKWebView.url), options: .new, context: nil)
            webView.isHidden = true
            webView.navigationDelegate = self
            webView.load(URLRequest(url: url));
            self._webView = webView;
        }
       
        modalPresentationStyle = .fullScreen
        view.backgroundColor = .clear
        
        view.frame = parentViewController.view.bounds
        
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    

   

    @objc func closeButtonTapped() {
            dismiss(animated: true, completion: nil)
    }
    
    @objc public override func dismiss(animated flag: Bool, completion: (() -> Void)? = nil) {
        
        super.dismiss(animated: flag, completion: completion);
        self._options.resolveCall(self._webView?.url);
        self._pluginEvents.notifyClosed(self._webView?.url)
    }
    
    @objc private func setupToolbar(_ options: WebViewToolbarOptions) -> UIToolbar {
        let toolbar = UIToolbar()
        toolbar.translatesAutoresizingMaskIntoConstraints = false
        toolbar.backgroundColor = UIColor(hex: options.backgroundColor)
        view.addSubview(toolbar)
        
        // Add close button
        let closeButton = UIButton(type: .system)
        closeButton.setImage(UIImage(systemName: "xmark"), for: .normal)
        //closeButton.setTitle("Done", for: .normal)
        closeButton.addTarget(self, action: #selector(closeButtonTapped), for: .touchUpInside)
        closeButton.translatesAutoresizingMaskIntoConstraints = false
        closeButton.setTitleColor(UIColor(hex: options.color), for: .normal)
        
        
        let buttonSize: CGFloat = 24 // Set the desired size of the button
        closeButton.frame = CGRect(x: 0, y: 0, width: buttonSize, height: buttonSize)
        
        toolbar.addSubview(closeButton)
        
        // Add title label
        let titleLabel = UILabel()
        titleLabel.text = title
        titleLabel.textAlignment = .center
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.textColor = UIColor(hex: options.color)
        toolbar.addSubview(titleLabel)
      
  
        // Layout constraints
        let guide = view.safeAreaLayoutGuide
        NSLayoutConstraint.activate([
            toolbar.topAnchor.constraint(equalTo: guide.topAnchor),
            toolbar.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            toolbar.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            toolbar.heightAnchor.constraint(equalToConstant: 44),
            
            closeButton.leadingAnchor.constraint(equalTo: toolbar.leadingAnchor, constant: 8),
            closeButton.centerYAnchor.constraint(equalTo: toolbar.centerYAnchor),
            
            titleLabel.leadingAnchor.constraint(equalTo: closeButton.trailingAnchor, constant: 8),
            titleLabel.centerYAnchor.constraint(equalTo: toolbar.centerYAnchor),
        ])
        
        return toolbar;
    }
   
   
    @objc public override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        if keyPath == #keyPath(WKWebView.url), let newURL = change?[.newKey] as? URL {
            self._pluginEvents.notifyUrlChanged(newURL)
           
        }
    }
    
    deinit {
          // Remove observer to avoid memory leaks
        self._webView?.removeObserver(self, forKeyPath: #keyPath(WKWebView.url))
    }
}



extension UIColor {
    convenience init(hex: String) {
        var hexSanitized = hex.trimmingCharacters(in: .whitespacesAndNewlines)
        hexSanitized = hexSanitized.replacingOccurrences(of: "#", with: "")
        
        var rgb: UInt64 = 0
        
        Scanner(string: hexSanitized).scanHexInt64(&rgb)
        
        let red = CGFloat((rgb & 0xFF0000) >> 16) / 255.0
        let green = CGFloat((rgb & 0x00FF00) >> 8) / 255.0
        let blue = CGFloat(rgb & 0x0000FF) / 255.0
        
        self.init(red: red, green: green, blue: blue, alpha: 1.0)
    }
}

extension WebViewController: WKNavigationDelegate {
    
    @objc public func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
     
        if(self._isInitialPageLoad) {
            _activityIndicatorView?.startAnimating()
           
            webView.isHidden = true
        }
      
    }
    
    @objc public func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        
        if(self._isInitialPageLoad) {
            self._isInitialPageLoad = false;
            _activityIndicatorView?.stopAnimating()
            webView.isHidden = false
        }
       
    }
    
    

}
