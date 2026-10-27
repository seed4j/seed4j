import { AppVue } from '@/root/infrastructure/primary';
import { type VueWrapper, shallowMount } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

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
