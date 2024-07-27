import ConsoleLogger from '@/shared/logger/infrastructure/secondary/ConsoleLogger';
import { describe, it, expect, vi } from 'vitest';

describe('ConsoleLogger', () => {
  it('should log an error', () => {
    const logger = {
      error: vi.fn(),
    };
    const consoleLogger = new ConsoleLogger(logger as any);
    const error = new Error('Error message');

    consoleLogger.error('An error occurs', error);

    expect(logger.error).toHaveBeenCalledTimes(1);
    expect(logger.error).toBeCalledWith('An error occurs\n', error);
  });
});
