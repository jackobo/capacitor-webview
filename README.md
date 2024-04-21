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
* [`closeWebview()`](#closewebview)
* [`reload()`](#reload)
* [`navigateTo(...)`](#navigateto)
* [`addListener('urlChanged', ...)`](#addlistenerurlchanged-)
* [`addListener('webviewClosed', ...)`](#addlistenerwebviewclosed-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### openWebView(...)

```typescript
openWebView(options: IOpenWebviewOptions) => Promise<void>
```

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#iopenwebviewoptions">IOpenWebviewOptions</a></code> |

--------------------


### closeWebview()

```typescript
closeWebview() => Promise<void>
```

--------------------


### reload()

```typescript
reload() => Promise<void>
```

--------------------


### navigateTo(...)

```typescript
navigateTo(options: { url: string; }) => Promise<void>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ url: string; }</code> |

--------------------


### addListener('urlChanged', ...)

```typescript
addListener(eventName: 'urlChanged', handler: UrlChangeEventHandler) => Promise<PluginListenerHandle>
```

| Param           | Type                                                                    |
| --------------- | ----------------------------------------------------------------------- |
| **`eventName`** | <code>'urlChanged'</code>                                               |
| **`handler`**   | <code><a href="#urlchangeeventhandler">UrlChangeEventHandler</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### addListener('webviewClosed', ...)

```typescript
addListener(eventName: 'webviewClosed', handler: WebviewClosedEventHandler) => Promise<PluginListenerHandle>
```

| Param           | Type                                                                            |
| --------------- | ------------------------------------------------------------------------------- |
| **`eventName`** | <code>'webviewClosed'</code>                                                    |
| **`handler`**   | <code><a href="#webviewclosedeventhandler">WebviewClosedEventHandler</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

--------------------


### Interfaces


#### IOpenWebviewOptions

| Prop                        | Type                                                                      | Description                                                                            | Default            | Since |
| --------------------------- | ------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`url`**                   | <code>string</code>                                                       | URL to load in the Webview                                                             |                    | 0.0.1 |
| **`headers`**               | <code><a href="#record">Record</a>&lt;string, string&gt;</code>           | Headers to append to the request                                                       |                    | 0.0.1 |
| **`showAfterPageIsLoaded`** | <code>boolean</code>                                                      | showAfterPageIsLoaded: if true the webview will be shown only after the page is loaded | <code>true</code>  | 0.0.1 |
| **`allowDebug`**            | <code>boolean</code>                                                      | Whether to enable debug on the webview                                                 | <code>false</code> | 0.0.1 |
| **`toolbar`**               | <code><a href="#iwebviewtoolbaroptions">IWebViewToolbarOptions</a></code> | Specify toolbar options. If null or undefined the toolbar will not be shown            |                    | 0.0.1 |


#### IWebViewToolbarOptions

| Prop                  | Type                | Description                                                  | Since |
| --------------------- | ------------------- | ------------------------------------------------------------ | ----- |
| **`title`**           | <code>string</code> | The text to appear in the toolbar                            | 0.0.1 |
| **`backgroundColor`** | <code>string</code> | Background color of the toolbar in hex format                | 0.0.1 |
| **`color`**           | <code>string</code> | The color for the title and for the back arrow in hex format | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### UrlChangedEvent

Emit when the url changes in the Webview

| Prop      | Type                | Description                              | Since |
| --------- | ------------------- | ---------------------------------------- | ----- |
| **`url`** | <code>string</code> | Emit when the url changes in the Webview | 0.0.1 |


#### WebviewClosedEvent

Emit when the Webview is closed

| Prop             | Type                | Description                                         |
| ---------------- | ------------------- | --------------------------------------------------- |
| **`currentUrl`** | <code>string</code> | Current URL in the Webview by the time it is closed |


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>


#### UrlChangeEventHandler

Handler for urlChanged event

<code>(state: <a href="#urlchangedevent">UrlChangedEvent</a>): void</code>


#### WebviewClosedEventHandler

<code>(state: <a href="#webviewclosedevent">WebviewClosedEvent</a>): void</code>

</docgen-api>
