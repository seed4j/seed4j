import { describe, expect, it, vi } from 'vitest';

import ConsoleLogger from '@/shared/logger/infrastructure/secondary/ConsoleLogger';

describe('ConsoleLogger', () => {
  it('should log an error', () => {
    const logger = {
      error: vi.fn(),
    };
    const consoleLogger = new ConsoleLogger(logger as any);
    const error = new Error('Error message');

    consoleLogger.error('An error occurs', error);

    expect(logger.error).toHaveBeenCalledExactlyOnceWith('An error occurs\n', error);
  });
});
