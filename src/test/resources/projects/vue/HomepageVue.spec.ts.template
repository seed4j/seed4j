import HomepageVue from '@/home/infrastructure/primary/HomepageVue.vue';
import { shallowMount, type VueWrapper } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

let wrapper: VueWrapper;

const wrap = () => {
  wrapper = shallowMount(HomepageVue);
};

describe('App I18next', () => {
  it('should renders with translation', () => {
    wrap();

    expect(wrapper.text()).toContain('translationEnabled');
  });
});
