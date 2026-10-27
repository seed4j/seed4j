import { WindowApplicationListener } from '@/shared/alert/infrastructure/primary/WindowApplicationListener';
import { describe, expect, it, vi } from 'vitest';

describe('WindowApplicationListener', () => {
  it('should add event listener on window', () => {
    const addEventListener = vi.spyOn(window, 'addEventListener');

    const windowApplicationListener = new WindowApplicationListener(window);

    windowApplicationListener.addEventListener('success', vi.fn<EventListener>());

    expect(addEventListener).toHaveBeenCalledOnce();
  });

  it('should remove event listener on window', () => {
    const removeEventListener = vi.spyOn(window, 'removeEventListener');

    const windowApplicationListener = new WindowApplicationListener(window);

    windowApplicationListener.removeEventListener('success', vi.fn<EventListener>());

    expect(removeEventListener).toHaveBeenCalledOnce();
  });
});
