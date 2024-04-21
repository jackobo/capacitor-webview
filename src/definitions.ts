export interface CapacitorWebviewPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
