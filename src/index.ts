import { registerPlugin } from '@capacitor/core';

import type { CapacitorWebviewPlugin } from './definitions';

const CapacitorWebview = registerPlugin<CapacitorWebviewPlugin>('CapacitorWebview', {
  web: () => import('./web').then(m => new m.CapacitorWebviewWeb()),
});

export * from './definitions';
export { CapacitorWebview };
