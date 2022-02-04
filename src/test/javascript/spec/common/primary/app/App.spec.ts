import { mount } from '@vue/test-utils';
import AppVue from '../../../../../../main/webapp/app/common/primary/app/App.vue';

describe('App', () => {
  it('should display header text', () => {
    const wrapper = mount(AppVue, { props: {} });

    expect(wrapper.exists()).toBe(true);
  });
});
