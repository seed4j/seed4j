import { IconVue } from '@/shared/icon/infrastructure/primary';
import { shallowMount } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

describe('Icon', () => {
  it('should exist', () => {
    const wrapper = shallowMount(IconVue, {
      props: {
        name: 'icon-name',
        ariaHidden: true,
        ariaLabel: 'icon-label',
      },
    });

    expect(wrapper.exists()).toBe(true);
  });
});
