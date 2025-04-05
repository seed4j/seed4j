import { WindowApplicationListener } from '@/shared/alert/infrastructure/primary/WindowApplicationListener';
import { describe, expect, it, vi } from 'vitest';

describe('WindowApplicationListener', () => {
  it.each(['addEventListener', 'removeEventListener'] as const)('should %s on window', method => {
    const spy = vi.spyOn(window, method).mockImplementation(vi.fn());

    const windowApplicationListener = new WindowApplicationListener(window);

    windowApplicationListener[method]('success', vi.fn());

    expect(spy).toHaveBeenCalledOnce();
  });
});
