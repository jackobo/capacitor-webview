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
    
    private let _options: OpenWebViewOptions
    private let _pluginEvents: CapacitorWebViewPluginEvents
    
    private var _webView: WKWebView? = nil
    private var _activityIndicatorView: UIActivityIndicatorView?
    
    private var _isInitialPageLoad: Bool = true
    
    
    init(options: OpenWebViewOptions, parentViewController: UIViewController, pluginEvents: CapacitorWebViewPluginEvents) {
       
        self._options = options
        self._pluginEvents = pluginEvents
        
        super.init(nibName: nil, bundle: nil)
        
        let toolbar = setupToolbar()
        self._activityIndicatorView = setupSpinner()
        self._webView = setupWebView(toolbar: toolbar)
        
        modalPresentationStyle = .fullScreen
        view.backgroundColor = .clear
        
        view.frame = parentViewController.view.bounds
        
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    

    @objc private func closeButtonTapped() {
        dismiss(animated: true, completion: nil)
    }
    
    @objc public override func dismiss(animated flag: Bool, completion: (() -> Void)? = nil) {
        super.dismiss(animated: flag, completion: completion);
        self._options.resolveCall(self._webView?.url);
        self._pluginEvents.notifyClosed(self._webView?.url)
    }
    
   
    @objc public override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        if keyPath == #keyPath(WKWebView.url), let newURL = change?[.newKey] as? URL {
            self._pluginEvents.notifyUrlChanged(newURL)
        }
    }
    
    deinit {
        self._webView?.removeObserver(self, forKeyPath: #keyPath(WKWebView.url))
    }
    
    
    @objc private func createCloseButton(_ toolbarOptions: WebViewToolbarOptions) -> UIButton {
        let closeButton = UIButton(type: .system)
        
        closeButton.setImage(UIImage(systemName: "xmark"), for: .normal)
        closeButton.addTarget(self, action: #selector(closeButtonTapped), for: .touchUpInside)
        //closeButton.translatesAutoresizingMaskIntoConstraints = false
        let color = UIColor(hex: toolbarOptions.color)
        closeButton.setTitleColor(color, for: .normal)
        closeButton.tintColor = color
            
        return closeButton
    }
    
    @objc private func createTitleLable(_ toolbarOptions: WebViewToolbarOptions) -> UILabel {
        let titleLabel = UILabel()
        titleLabel.text = toolbarOptions.title
        titleLabel.textAlignment = .center
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.textColor = UIColor(hex: toolbarOptions.color)
        return titleLabel
    }
    
    @objc private func setupToolbar() -> UIToolbar? {
        
        guard  let toolbarOptions = self._options.toolbar else {
            return nil
        }
        
        let toolbar = UIToolbar()
        toolbar.translatesAutoresizingMaskIntoConstraints = false
        toolbar.barTintColor = UIColor(hex: toolbarOptions.backgroundColor)
        view.addSubview(toolbar)
        
        let flexibleSpaceLeft = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        let flexibleSpaceRight = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        
        let closeButton = UIBarButtonItem(customView: createCloseButton(toolbarOptions))
        //toolbar.addSubview(closeButton)
        
        let titleLabel = UIBarButtonItem(customView: createTitleLable(toolbarOptions))
        //toolbar.addSubview(titleLabel)
      
        toolbar.setItems([closeButton, flexibleSpaceLeft, titleLabel, flexibleSpaceRight], animated: true)
  
        // Layout constraints
        let guide = view.safeAreaLayoutGuide
        
        NSLayoutConstraint.activate([
            toolbar.topAnchor.constraint(equalTo: guide.topAnchor),
            toolbar.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            toolbar.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            toolbar.heightAnchor.constraint(equalToConstant: 44),
            
            /*
            closeButton.leadingAnchor.constraint(equalTo: toolbar.leadingAnchor, constant: 8),
            closeButton.centerYAnchor.constraint(equalTo: toolbar.centerYAnchor),
            
            closeButton.widthAnchor.constraint(equalToConstant: buttonSize),
            closeButton.heightAnchor.constraint(equalToConstant: buttonSize),
            
            titleLabel.leadingAnchor.constraint(equalTo: closeButton.trailingAnchor, constant: 8),
            titleLabel.centerYAnchor.constraint(equalTo: toolbar.centerYAnchor),
             */
        ])
        
        return toolbar
        
    }
    
    @objc private func setupSpinner() -> UIActivityIndicatorView {
        let spinnerView = UIActivityIndicatorView(style: .medium)
        spinnerView.hidesWhenStopped = true
        spinnerView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(spinnerView)
        spinnerView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        spinnerView.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
        return spinnerView
    }
    
    @objc private func setupWebView(toolbar: UIToolbar?) -> WKWebView? {
        
        guard let url = URL(string: self._options.url) else {
            return nil
        }
                
        let webView = WKWebView(frame: UIScreen.main.bounds);
        
        if let userAgent = self._options.userAgent {
            webView.configuration.applicationNameForUserAgent = userAgent
        }
        
        if self._options.allowDebug {
            if webView.responds(to: Selector(("setInspectable:"))) {
                // Fix: https://stackoverflow.com/questions/76216183/how-to-debug-wkwebview-in-ios-16-4-1-using-xcode-14-2/76603043#76603043
                webView.perform(Selector(("setInspectable:")), with: true)
            }
        }
        
                
        
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
        var request = URLRequest(url: url)
        
        for (key, value) in self._options.headers {
            request.setValue(value, forHTTPHeaderField: key)
        }
        
        
        
        webView.load(request)
        return webView
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
