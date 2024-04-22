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
        
        var toolbar: UIToolbar? = nil
        if let toolBarOptions = options.toolbar {
            toolbar = setupToolbar(toolBarOptions);
            
        }
        
        let webView = WKWebView(frame: UIScreen.main.bounds);
        
        if let url = URL(string: options.url) {
            
            view.addSubview(webView)
            webView.translatesAutoresizingMaskIntoConstraints = false
            
          
             // Layout constraints for WKWebView
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
            
            webView.load(URLRequest(url: url));
            
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

