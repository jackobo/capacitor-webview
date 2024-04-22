import { WebPlugin } from '@capacitor/core';

import type {CapacitorWebviewPlugin, IOpenWebViewOptions, IOpenWebViewResponse} from './definitions';

export class CapacitorWebviewWeb extends WebPlugin implements CapacitorWebviewPlugin {

  private _logMethodCall(methodName: string, ...parameters: any[]): void {
    console.log(`${methodName} not supported on web`, parameters);
  }
  async openWebView(options: IOpenWebViewOptions): Promise<IOpenWebViewResponse> {
      this._logMethodCall('openWebview', options);
      return {
        url: ""
      };
  }
  async closeWebView(): Promise<void> {
    this._logMethodCall('closeWebview');
  }
}
