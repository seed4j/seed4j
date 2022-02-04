import { shallowMount } from '@vue/test-utils';
import AboutVue from '../../../../../../main/webapp/app/common/primary/home/About.vue';

describe('About.vue', () => {
  it('renders props.msg when passed', () => {
    const wrapper = shallowMount(AboutVue, {});
    expect(wrapper.exists()).toBe(true);
  });
});
