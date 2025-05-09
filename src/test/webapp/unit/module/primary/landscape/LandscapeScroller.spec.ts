import { beforeEach, describe, expect, it, vi } from 'vitest';

import { LandscapeScroller } from '@/module/primary/landscape/LandscapeScroller';

interface HTMLElementStub extends HTMLElement {
  scroll: vi.fn;
  scrollIntoView: vi.fn;
}
const stubHtmlElement = (): HTMLElementStub =>
  ({
    scroll: vi.fn(),
    scrollIntoView: vi.fn(),
  }) as HTMLElementStub;

describe('LandscapeScroller', () => {
  let landscapeScroller: LandscapeScroller;
  let element: HTMLElementStub;

  beforeEach(() => {
    landscapeScroller = new LandscapeScroller();
    element = stubHtmlElement();
  });

  it('should scroll', () => {
    landscapeScroller.scroll(element, 10, 10);

    expect(element.scroll).toHaveBeenCalledExactlyOnceWith(10, 10);
  });

  it('should scroll smooth', () => {
    landscapeScroller.scrollSmooth(element, 10, 10);

    expect(element.scroll).toHaveBeenCalledExactlyOnceWith({ left: 10, top: 10, behavior: 'smooth' });
  });

  it('should scroll into view', () => {
    landscapeScroller.scrollIntoView(element);

    expect(element.scrollIntoView).toHaveBeenCalledExactlyOnceWith({ behavior: 'smooth', block: 'center', inline: 'center' });
  });
});
