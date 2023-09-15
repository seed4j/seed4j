import { LandscapeMiniMapVue } from '@/module/primary/landscape-minimap';
import { mount } from '@vue/test-utils';
import { describe, it, expect, vi } from 'vitest';
import { wrappedElement } from '../../../WrappedElement';
import sinon, { SinonStub } from 'sinon';

const buildLandscapeContainer = (): HTMLDivElement => {
  const landscapeContainer = document.createElement('div');

  landscapeContainer.style.width = '1000px';
  landscapeContainer.style.height = '1000px';
  landscapeContainer.style.overflow = 'auto';

  for (let i = 0; i < 10; i++) {
    const c = document.createElement('div');

    c.style.width = '1000px';
    c.style.height = '1000px';

    landscapeContainer.appendChild(c);
  }

  document.body.appendChild(landscapeContainer);

  return landscapeContainer;
};

describe('MiniMap component', () => {
  it('should scroll the landscape container if minimapViewer position has changed', async () => {
    const landscapeContainer = buildLandscapeContainer();
    (landscapeContainer.scroll as SinonStub) = sinon.stub();

    const wrapper = mount(LandscapeMiniMapVue, {
      props: { landscapeContainer: landscapeContainer },
    });

    const minimapViewer = wrapper.find(wrappedElement('minimap-viewer'));

    const { x, y } = minimapViewer.element.getBoundingClientRect();
    const mouseDownEvent = {
      clientX: x + 10,
      clientY: y + 10,
    };

    await minimapViewer.trigger('mousedown', mouseDownEvent);
    await wrapper.vm.$nextTick();
    await minimapViewer.trigger('mousemove', <MouseEventInit>{ clientX: x + 20, clientY: y + 20 });
    await wrapper.vm.$nextTick();
    await minimapViewer.trigger('mouseup');
    await wrapper.vm.$nextTick();
    await minimapViewer.trigger('mouseleave');

    expect((landscapeContainer.scroll as SinonStub).called).toBe(true);
  });

  it('should not scroll the landscape container if minimapViewer is not being grabbed', async () => {
    const landscapeContainer = buildLandscapeContainer();

    (landscapeContainer.scroll as SinonStub) = sinon.stub();

    const wrapper = mount(LandscapeMiniMapVue, {
      props: { landscapeContainer: landscapeContainer },
    });

    const minimapViewer = wrapper.find(wrappedElement('minimap-viewer'));

    await minimapViewer.trigger('mousemove');
    await wrapper.vm.$nextTick();

    expect((landscapeContainer.scroll as SinonStub).called).toBe(false);
  });

  it('should track the scroller position of the landscape container', async () => {
    const landscapeContainer = buildLandscapeContainer();

    vi.spyOn(landscapeContainer, 'addEventListener');

    mount(LandscapeMiniMapVue, {
      props: { landscapeContainer: landscapeContainer },
    });

    landscapeContainer.dispatchEvent(new Event('scroll'));

    expect(landscapeContainer.addEventListener).toBeCalled();
  });
});
