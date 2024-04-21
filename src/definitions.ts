import type {PluginListenerHandle} from "@capacitor/core";

export interface IWebViewToolbarOptions {
  /**
   * The text to appear in the toolbar
   * @since 0.0.1
   */
  title: string;
  /**
   * Background color of the toolbar in hex format
   * @since 0.0.1
   */
  backgroundColor: string;

  /**
   * The color for the title and for the back arrow in hex format
   * @since 0.0.1
   */
  color: string;
}

export interface IOpenWebviewOptions {
  /**
   * URL to load in the Webview
   * @since 0.0.1
   */
  url: string;

  /**
   * Headers to append to the request
   * @since 0.0.1
   */
  headers?: Record<string, string>;

  /**
   * showAfterPageIsLoaded: if true the webview will be shown only after the page is loaded
   * @since 0.0.1
   * @default true
   */
  showAfterPageIsLoaded?: boolean;

  /**
   * Whether to enable debug on the webview
   * @since 0.0.1
   * @default false
   */
  allowDebug?: boolean;

  /**
   * Specify toolbar options. If null or undefined the toolbar will not be shown
   * @since 0.0.1
   */
  toolbar?: IWebViewToolbarOptions;

}


/**
 * Emit when the url changes in the Webview
 * @since 0.0.1
 */
export interface UrlChangedEvent {
  /**
   * Emit when the url changes in the Webview
   *
   * @since 0.0.1
   */
  url: string;
}

/**
 * Emit when the Webview is closed
 *
 * @since 0.0.1
 */
export interface WebviewClosedEvent {
  /**
   * Current URL in the Webview by the time it is closed
   */
  currentUrl: string;
}

/**
 * Handler for urlChanged event
 * @since 0.0.1
 */
export type UrlChangeEventHandler = (state: UrlChangedEvent) => void;
export type WebviewClosedEventHandler = (state: WebviewClosedEvent) => void;

export interface CapacitorWebviewPlugin {
  openWebView(options: IOpenWebviewOptions): Promise<void>;
  closeWebview(): Promise<void>;
  reload(): Promise<void>;
  navigateTo(options: {url: string}): Promise<void>;
  addListener(eventName: 'urlChanged', handler: UrlChangeEventHandler): Promise<PluginListenerHandle>;
  addListener(eventName: 'webviewClosed', handler: WebviewClosedEventHandler): Promise<PluginListenerHandle>;
  removeAllListeners(): Promise<void>;
}
