import type {PluginListenerHandle} from "@capacitor/core";

export interface IWebViewToolbarOptions {
  /**
   * The text to appear in the toolbar
   * @since 1.0.0
   */
  title: string;
  /**
   * Background color of the toolbar in hex format
   * @since 1.0.0
   */
  backgroundColor: string;

  /**
   * The color for the title text and for the X close button in hex format
   * @since 1.0.0
   */
  color: string;
}

export interface IOpenWebViewOptions {
  /**
   * URL to load in the Webview
   * @since 1.0.0
   */
  url: string;

  /**
   * Headers to be appended to the initial request. These are not used for subsequent navigation inside the WebView.
   * @since 1.0.0
   */
  headers?: Record<string, string>;


  /**
   * Whether to enable debug on the webview (iOS only)
   * @since 1.0.0
   * @default false
   */
  enableDebug?: boolean;

  /**
   * Specify toolbar options. If null or undefined the toolbar will not be shown
   * @since 1.0.0
   */
  toolbar?: IWebViewToolbarOptions;

  /**
   * When set to true will disable the SSL errors (Android only)
   * It should be used only for testing purpose in test environments if you encounter certificates issues
   * Only SSL_UNTRUSTED errors are ignored when this property is set to true
   * @default false
   *
   * @since 1.0.0
   */
  ignoreSslErrors?: boolean;

  /**
   * An array of partial matching URLs that will be open in the system browser when an open in new tab request happens inside the webview. (Android only)
   * This property is ignored when navigation is a server side redirect.
   * Example:
   * urlPatternsToOpenInExternalBrowser: ['/cookies-policy', '/privacy-notice'] // Any URL navigation with open in new tab that contains any of these segments will be open in the device default browser
   */
  urlPatternsToOpenInExternalBrowser?: string[];
}

/**
 * The result of the openWebView method
 */
export interface IOpenWebViewResponse {
  /**
   * The URL in the WebView by the time is closed
   *
   * @since 1.0.0
   */
  url: string;
}

/**
 * Emit when the url changes in the Webview
 */
export interface UrlChangedEvent {
  /**
   * The new URL in the WebView
   *
   * @since 1.0.0
   */
  url: string;
}

/**
 * Emit when the Webview is closed
 *
 * @since 1.0.0
 */
export interface WebViewClosedEvent {
  /**
   * Current URL in the Webview by the time it is closed
   *
   * @since 1.0.0
   */
  url: string;
}

/**
 * Handler for urlChanged event
 *
 * @since 1.0.0
 */
export type UrlChangedEventHandler = (state: UrlChangedEvent) => void;

/**
 * Handler for webViewClosedEvent event
 *
 * @since 1.0.0
 */
export type WebViewClosedEventHandler = (state: WebViewClosedEvent) => void;


/**
 * A capacitor plugin for opening a second WebView inside your app.
 * Android and iOS only.
 */
export interface CapacitorWebviewPlugin {
  /**
   * Opens a new WebView using parameters specified in options.
   * After opening the WebView the urlChanged event is also triggered for the initial URL provided in the options parameter.
   * Method resolves when the WebView is closed either by the user or programatically by calling closeWebView.
   * The response contains the URL that was in the WebView by the time of closing it.
   * @param options
   * @returns Promise<IOpenWebViewResponse>
   *
   * @since 1.0.0
   */
  openWebView(options: IOpenWebViewOptions): Promise<IOpenWebViewResponse>;

  /**
   * Programmatically closes the WebView. The webViewClosed event is triggered also when calling this method.
   */
  closeWebView(): Promise<void>;

  /**
   * Subscribes to urlChanged event.
   * This event is triggered when navigation occurs inside the WebView
   * @param eventName
   * @param handler
   *
   * @since 1.0.0
   */
  addListener(eventName: 'urlChanged', handler: UrlChangedEventHandler): Promise<PluginListenerHandle>;

  /**
   * Subscribes to webViewClosed event.
   * This event is triggered when user closes the WebView or when the WebView is closed programmatically by calling closeWebView
   * @param eventName
   * @param handler
   *
   * @since 1.0.0
   */
  addListener(eventName: 'webViewClosed', handler: WebViewClosedEventHandler): Promise<PluginListenerHandle>;

  /**
   * Removes all events subscriptions
   *
   * @since 1.0.0
   */
  removeAllListeners(): Promise<void>;
}
