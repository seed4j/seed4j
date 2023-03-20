import { HeaderVue } from '@/common/primary/header';
import { shallowMount, VueWrapper } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';

const wrap = (): VueWrapper => {
  return shallowMount(HeaderVue, {
    global: {
      stubs: ['router-link'],
    },
  });
};

describe('Header', () => {
  it('should exist', () => {
    const wrapper = wrap();

    expect(wrapper.exists()).toBe(true);
  });
});
