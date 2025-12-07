import { LOGGER, provideForLogger } from '@/shared/logger/application/LoggerProvider';
import ConsoleLogger from '@/shared/logger/infrastructure/secondary/ConsoleLogger';
import { describe, expect, it, vi } from 'vitest';

const provideMock = vi.fn();
vi.mock('@/injections', () => ({
  provide: (key: any, value: any) => provideMock(key, value),
}));

describe('LoggerProvider', () => {
  it('should provide logger', () => {
    provideForLogger();

    expect(provideMock).toHaveBeenCalledWith(LOGGER, expect.any(ConsoleLogger));
  });
});
