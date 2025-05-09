import { describe, expect, it, vi } from 'vitest';

import { WindowApplicationListener } from '@/shared/alert/infrastructure/primary/WindowApplicationListener';

describe('WindowApplicationListener', () => {
  it('should add event listener on window', () => {
    vi.spyOn(window, 'addEventListener').mockImplementation(vi.fn());

    const windowApplicationListener = new WindowApplicationListener(window);

    windowApplicationListener.addEventListener('success', vi.fn());

    expect(window.addEventListener).toHaveBeenCalledOnce();
  });

  it('should remove event listener on window', () => {
    vi.spyOn(window, 'removeEventListener').mockImplementation(vi.fn());

    const windowApplicationListener = new WindowApplicationListener(window);

    windowApplicationListener.removeEventListener('success', vi.fn());

    expect(window.removeEventListener).toHaveBeenCalledOnce();
  });
});
