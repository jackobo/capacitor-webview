# @jackobo/capacitor-webview

Capacitor plugin for Webview

## Install

```bash
npm install @jackobo/capacitor-webview
npx cap sync
```

## API

<docgen-index>

* [`openWebView(...)`](#openwebview)
* [`closeWebView()`](#closewebview)
* [`addListener('urlChanged', ...)`](#addlistenerurlchanged-)
* [`addListener('webViewClosed', ...)`](#addlistenerwebviewclosed-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

A capacitor plugin for opening a second WebView inside your app.
Android and iOS only.

### openWebView(...)

```typescript
openWebView(options: IOpenWebviewOptions) => Promise<void>
```

Opens a new WebView using parameters specified in options.
After opening the WebView the urlChanged event is also triggered for the initial URL provided in the options parameter.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#iopenwebviewoptions">IOpenWebviewOptions</a></code> |

**Since:** 1.0.0

--------------------


### closeWebView()

```typescript
closeWebView() => Promise<void>
```

Programmatically closes the WebView. The webViewClosed event is triggered also when closing it programmatically.

--------------------


### addListener('urlChanged', ...)

```typescript
addListener(eventName: 'urlChanged', handler: UrlChangedEventHandler) => Promise<PluginListenerHandle>
```

Subscribes to urlChanged event.
This event is triggered when navigation occurs inside the WebView

| Param           | Type                                                                      |
| --------------- | ------------------------------------------------------------------------- |
| **`eventName`** | <code>'urlChanged'</code>                                                 |
| **`handler`**   | <code><a href="#urlchangedeventhandler">UrlChangedEventHandler</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.0.0

--------------------


### addListener('webViewClosed', ...)

```typescript
addListener(eventName: 'webViewClosed', handler: WebViewClosedEventHandler) => Promise<PluginListenerHandle>
```

Subscribes to webViewClosed event.
This event is triggered when user closes the WebView or when the WebView is closed programmatically by calling closeWebView

| Param           | Type                                                                            |
| --------------- | ------------------------------------------------------------------------------- |
| **`eventName`** | <code>'webViewClosed'</code>                                                    |
| **`handler`**   | <code><a href="#webviewclosedeventhandler">WebViewClosedEventHandler</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.0.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Removes all events subscriptions

**Since:** 1.0.0

--------------------


### Interfaces


#### IOpenWebviewOptions

| Prop             | Type                                                                      | Description                                                                                                     | Default            | Since |
| ---------------- | ------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`url`**        | <code>string</code>                                                       | URL to load in the Webview                                                                                      |                    | 1.0.0 |
| **`headers`**    | <code><a href="#record">Record</a>&lt;string, string&gt;</code>           | Headers to be appended to the initial request. These are not used for subsequent navigation inside the WebView. |                    | 1.0.0 |
| **`allowDebug`** | <code>boolean</code>                                                      | Whether to enable debug on the webview (iOS only)                                                               | <code>false</code> | 1.0.0 |
| **`toolbar`**    | <code><a href="#iwebviewtoolbaroptions">IWebViewToolbarOptions</a></code> | Specify toolbar options. If null or undefined the toolbar will not be shown                                     |                    | 1.0.0 |


#### IWebViewToolbarOptions

| Prop                  | Type                | Description                                                  | Since |
| --------------------- | ------------------- | ------------------------------------------------------------ | ----- |
| **`title`**           | <code>string</code> | The text to appear in the toolbar                            | 1.0.0 |
| **`backgroundColor`** | <code>string</code> | Background color of the toolbar in hex format                | 1.0.0 |
| **`color`**           | <code>string</code> | The color for the title and for the back arrow in hex format | 1.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### UrlChangedEvent

Emit when the url changes in the Webview

| Prop      | Type                | Description                | Since |
| --------- | ------------------- | -------------------------- | ----- |
| **`url`** | <code>string</code> | The new URL in the WebView | 1.0.0 |


#### WebViewClosedEvent

Emit when the Webview is closed

| Prop      | Type                | Description                                         |
| --------- | ------------------- | --------------------------------------------------- |
| **`url`** | <code>string</code> | Current URL in the Webview by the time it is closed |


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>


#### UrlChangedEventHandler

Handler for urlChanged event

<code>(state: <a href="#urlchangedevent">UrlChangedEvent</a>): void</code>


#### WebViewClosedEventHandler

Handler for webViewClosedEvent event

<code>(state: <a href="#webviewclosedevent">WebViewClosedEvent</a>): void</code>

</docgen-api>
