import { WebPlugin } from '@capacitor/core';

import {CapacitorWebviewPlugin, IOpenWebviewOptions} from './definitions';

export class CapacitorWebviewWeb extends WebPlugin implements CapacitorWebviewPlugin {

  private _logMethodCall(methodName: string, ...parameters: any[]): void {
    console.log(`${methodName} not supported on web`, parameters);
  }
  async openWebview(options: IOpenWebviewOptions): Promise<void> {
      this._logMethodCall('openWebview', options);
  }
  async closeWebview(): Promise<void> {
    this._logMethodCall('closeWebview');
  }
  async reload(): Promise<void> {
    this._logMethodCall('reload');
  }
  async navigateTo(options: { url: string; }): Promise<void> {
    this._logMethodCall('navigateTo', options);
  }
}
