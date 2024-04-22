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
    init(options: OpenWebViewOptions, parentViewController: UIViewController) {
       
        super.init(nibName: nil, bundle: nil);
        
        let webView = WKWebView(frame: UIScreen.main.bounds);
       
        
        if let url = URL(string: options.url) {
            view.addSubview(webView)
            webView.translatesAutoresizingMaskIntoConstraints = false
            webView.fillSuperview()
            webView.load(URLRequest(url: url));
           
           
        }
        
        modalPresentationStyle = .fullScreen
        view.backgroundColor = .clear
        
        view.frame = parentViewController.view.bounds
        
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
   
    
}

extension UIView {
    func fillSuperview() {
        guard let superview = superview else {
            return
        }
        NSLayoutConstraint.activate([
            topAnchor.constraint(equalTo: superview.topAnchor),
            leadingAnchor.constraint(equalTo: superview.leadingAnchor),
            trailingAnchor.constraint(equalTo: superview.trailingAnchor),
            bottomAnchor.constraint(equalTo: superview.bottomAnchor)
        ])
    }
}

