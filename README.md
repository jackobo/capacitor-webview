# @jackobo/capacitor-webview

Capacitor plugin for Android and iOS WebView,  
It opens an URL in a WebView.  
It doesn't provide navigation support in the WebView. No back and forward buttons just a close button and a title in a toolbar.  
You can control the toolbar background and text color.    

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
openWebView(options: IOpenWebViewOptions) => Promise<IOpenWebViewResponse>
```

Opens a new WebView using parameters specified in options.
After opening the WebView the urlChanged event is also triggered for the initial URL provided in the options parameter.
Method resolves when the WebView is closed either by the user or programatically by calling closeWebView.
The response contains the URL that was in the WebView by the time of closing it.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#iopenwebviewoptions">IOpenWebViewOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#iopenwebviewresponse">IOpenWebViewResponse</a>&gt;</code>

**Since:** 1.0.0

--------------------


### closeWebView()

```typescript
closeWebView() => Promise<void>
```

Programmatically closes the WebView. The webViewClosed event is triggered also when calling this method.

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


#### IOpenWebViewResponse

The result of the openWebView method

| Prop      | Type                | Description                                  | Since |
| --------- | ------------------- | -------------------------------------------- | ----- |
| **`url`** | <code>string</code> | The URL in the WebView by the time is closed | 1.0.0 |


#### IOpenWebViewOptions

| Prop                                     | Type                                                                      | Description                                                                                                                                                                                                                                                                                                                                                                                                                            | Default            | Since |
| ---------------------------------------- | ------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`url`**                                | <code>string</code>                                                       | URL to load in the Webview                                                                                                                                                                                                                                                                                                                                                                                                             |                    | 1.0.0 |
| **`headers`**                            | <code><a href="#record">Record</a>&lt;string, string&gt;</code>           | Headers to be appended to the initial request. These are not used for subsequent navigation inside the WebView.                                                                                                                                                                                                                                                                                                                        |                    | 1.0.0 |
| **`enableDebug`**                        | <code>boolean</code>                                                      | Whether to enable debug on the webview (iOS only)                                                                                                                                                                                                                                                                                                                                                                                      | <code>false</code> | 1.0.0 |
| **`toolbar`**                            | <code><a href="#iwebviewtoolbaroptions">IWebViewToolbarOptions</a></code> | Specify toolbar options. If null or undefined the toolbar will not be shown                                                                                                                                                                                                                                                                                                                                                            |                    | 1.0.0 |
| **`ignoreSslErrors`**                    | <code>boolean</code>                                                      | When set to true will disable the SSL errors (Android only) It should be used only for testing purpose in test environments if you encounter certificates issues Only SSL_UNTRUSTED errors are ignored when this property is set to true                                                                                                                                                                                               | <code>false</code> | 1.0.0 |
| **`urlPatternsToOpenInExternalBrowser`** | <code>string[]</code>                                                     | An array of partial matching URLs that will be open in the system browser when an open in new tab request happens inside the webview. (Android only) This property is ignored when navigation is a server side redirect. Example: urlPatternsToOpenInExternalBrowser: ['/cookies-policy', '/privacy-notice'] // Any URL navigation with open in new tab that contains any of these segments will be open in the device default browser |                    |       |


#### IWebViewToolbarOptions

| Prop                  | Type                | Description                                                           | Since |
| --------------------- | ------------------- | --------------------------------------------------------------------- | ----- |
| **`title`**           | <code>string</code> | The text to appear in the toolbar                                     | 1.0.0 |
| **`backgroundColor`** | <code>string</code> | Background color of the toolbar in hex format                         | 1.0.0 |
| **`color`**           | <code>string</code> | The color for the title text and for the X close button in hex format | 1.0.0 |


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

| Prop      | Type                | Description                                         | Since |
| --------- | ------------------- | --------------------------------------------------- | ----- |
| **`url`** | <code>string</code> | Current URL in the Webview by the time it is closed | 1.0.0 |


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
