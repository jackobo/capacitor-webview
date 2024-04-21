import {PluginListenerHandle} from "@capacitor/core";

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
   * @default false
   */
  showAfterPageIsLoaded?: boolean;

  /**
   * Whether to enable debug on the webview
   * @since 0.0.1
   * @default false
   */
  allowDebug?: boolean;


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
  openWebview(options: IOpenWebviewOptions): Promise<void>;
  closeWebview(): Promise<void>;
  reload(): Promise<void>;
  navigateTo(options: {url: string}): Promise<void>;
  addListener(eventName: 'urlChanged', handler: UrlChangeEventHandler): Promise<PluginListenerHandle>;
  addListener(eventName: 'webviewClosed', handler: WebviewClosedEventHandler): Promise<PluginListenerHandle>;
  removeAllListeners(): Promise<void>;
}
