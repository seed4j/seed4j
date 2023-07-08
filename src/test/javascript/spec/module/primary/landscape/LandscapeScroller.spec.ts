import { describe, expect, it } from 'vitest';
import sinon, { SinonStub } from 'sinon';
import { LandscapeScroller } from '@/module/primary/landscape/LandscapeScroller';

interface HTMLElementStub extends HTMLElement {
  scroll: SinonStub;
}
const stubHtmlElement = (): HTMLElementStub =>
  ({
    scroll: sinon.stub(),
  }) as HTMLElementStub;

describe('LandscapeScroller', () => {
  it('should scroll', () => {
    const element = stubHtmlElement();
    const landscapeScroller = new LandscapeScroller();

    landscapeScroller.scroll(element, 10, 10);

    const { args } = element.scroll.getCall(0);
    expect(args).toEqual([10, 10]);
  });
});
