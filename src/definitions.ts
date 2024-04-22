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
   * The color for the title and for the back arrow in hex format
   * @since 1.0.0
   */
  color: string;
}

export interface IOpenWebviewOptions {
  /**
   * URL to load in the Webview
   * @since 1.0.0
   */
  url: string;

  /**
   * Headers to append to the request
   * @since 1.0.0
   */
  headers?: Record<string, string>;


  /**
   * Whether to enable debug on the webview (iOS only)
   * @since 1.0.0
   * @default false
   */
  allowDebug?: boolean;

  /**
   * Specify toolbar options. If null or undefined the toolbar will not be shown
   * @since 1.0.0
   */
  toolbar?: IWebViewToolbarOptions;

}

/**
 * Emit when the url changes in the Webview
 * @since 1.0.0
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
   */
  url: string;
}

/**
 * Handler for urlChanged event
 * @since 1.0.0
 */
export type UrlChangedEventHandler = (state: UrlChangedEvent) => void;

/**
 * Handler for webViewClosedEvent event
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
   * @param options
   * @since 1.0.0
   */
  openWebView(options: IOpenWebviewOptions): Promise<void>;

  /**
   * Programmatically closes the WebView. The webViewClosed event is triggered also when closing it programmatically.
   */
  closeWebView(): Promise<void>;

  /**
   * Subscribes to urlChanged event.
   * This event is triggered when navigation occurs inside the WebView
   * @param eventName
   * @param handler
   * @since 1.0.0
   */
  addListener(eventName: 'urlChanged', handler: UrlChangedEventHandler): Promise<PluginListenerHandle>;

  /**
   * Subscribes to webViewClosed event.
   * This event is triggered when user closes the WebView or when the WebView is closed programmatically by calling closeWebView
   * @param eventName
   * @param handler
   * @since 1.0.0
   */
  addListener(eventName: 'webViewClosed', handler: WebViewClosedEventHandler): Promise<PluginListenerHandle>;

  /**
   * Removes all events subscriptions
   * @since 1.0.0
   */
  removeAllListeners(): Promise<void>;
}
