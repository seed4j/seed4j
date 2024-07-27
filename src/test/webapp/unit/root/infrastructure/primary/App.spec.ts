import { shallowMount, VueWrapper } from '@vue/test-utils';
import { AppVue } from '@/root/infrastructure/primary';
import { describe, it, expect } from 'vitest';

let wrapper: VueWrapper;

const wrap = () => {
  wrapper = shallowMount(AppVue, {
    global: {
      stubs: ['router-view'],
    },
  });
};

describe('App', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });
});
