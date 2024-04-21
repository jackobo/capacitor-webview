import { WebPlugin } from '@capacitor/core';

import type { CapacitorWebviewPlugin } from './definitions';

export class CapacitorWebviewWeb extends WebPlugin implements CapacitorWebviewPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
