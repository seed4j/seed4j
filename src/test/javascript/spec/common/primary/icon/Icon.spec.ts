import { shallowMount } from '@vue/test-utils';
import { IconVue } from '@/common/primary/icon';

describe('Icon', () => {
  it('should exist', () => {
    const wrapper = shallowMount(IconVue, {
      props: {
        name: 'icon-name',
      },
    });

    expect(wrapper.exists()).toBe(true);
  });
});
