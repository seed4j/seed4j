import { shallowMount } from '@vue/test-utils';
import { DefaultButtonVue } from '@/common/primary/default-button';

describe('DefaultButton', () => {
  it('should exist', () => {
    const wrapper = shallowMount(DefaultButtonVue, {
      props: {
        label: 'Button Name',
      },
    });

    expect(wrapper.exists()).toBe(true);
  });
});
