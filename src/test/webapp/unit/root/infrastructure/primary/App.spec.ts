import { shallowMount, type VueWrapper } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

import { AppVue } from '@/root/infrastructure/primary';

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
