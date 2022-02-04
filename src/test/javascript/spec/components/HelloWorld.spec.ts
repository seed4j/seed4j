// src/components/HelloWorld.spec.ts
import { mount } from '@vue/test-utils';
import HelloWorld from '../../../../main/webapp/app/components/HelloWorld.vue';

describe('HelloWorld', () => {
  it('should display header text', () => {
    const msg = 'Hello Vue 3';
    const wrapper = mount(HelloWorld, { props: { msg } });

    expect(wrapper.find('h1').text()).toEqual(msg);
  });
});
