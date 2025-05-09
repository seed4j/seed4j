import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest';

import { Timeout } from '@/shared/toast/domain/Timeout';

const TIMEOUT_TIME = 3000;
const LESS_TIME = 1000;

describe('Timeout', () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  it('should launch timeout after passed time', () => {
    const stub = vi.fn();
    new Timeout().register(stub, TIMEOUT_TIME);

    vi.advanceTimersByTime(TIMEOUT_TIME);

    expect(stub).toHaveBeenCalledOnce();
  });

  it('should not launch timeout with less some time', () => {
    const stub = vi.fn();
    new Timeout().register(stub, TIMEOUT_TIME);

    vi.advanceTimersByTime(LESS_TIME);

    expect(stub).not.toHaveBeenCalled();
  });

  it('should not launch timeout with unsubscribe', () => {
    const stub = vi.fn();
    const timeout = new Timeout();
    timeout.register(stub, TIMEOUT_TIME);

    timeout.unregister();
    vi.advanceTimersByTime(TIMEOUT_TIME);

    expect(stub).not.toHaveBeenCalled();
  });

  it('should not fail to unregister when not registered', () => {
    const timeout = new Timeout();

    expect(() => timeout.unregister()).not.toThrow();
  });

  it('should clear previous registration before register another one', () => {
    const timeout = new Timeout();
    const firstCall = vi.fn();
    const secondCall = vi.fn();

    timeout.register(firstCall, TIMEOUT_TIME);
    vi.advanceTimersByTime(LESS_TIME);
    timeout.register(secondCall, TIMEOUT_TIME);
    vi.advanceTimersByTime(TIMEOUT_TIME);

    expect(firstCall).not.toHaveBeenCalled();
    expect(secondCall).toHaveBeenCalledOnce();
  });
});
