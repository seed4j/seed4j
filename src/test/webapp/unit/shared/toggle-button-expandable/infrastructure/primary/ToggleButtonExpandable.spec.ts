import { mount } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

import { ToggleButtonExpandableVue } from '@/shared/toggle-button-expandable/infrastructure/primary';

describe('ToggleButtonExpandable', () => {
  describe('when initially rendered', () => {
    it('should display short name by default', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
        },
      });

      expect(wrapper.classes()).not.toContain('-active');
    });

    it('should display full name when initialized as active', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          isActive: true,
        },
      });

      expect(wrapper.classes()).toContain('-active');
    });
  });

  describe('when customizing appearance', () => {
    it('should highlight when active', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          isActive: true,
        },
      });

      expect(wrapper.classes()).toContain('-active');
    });

    it('should apply additional styling when provided', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          customClasses: ['-rank-color -s'],
        },
      });

      expect(wrapper.classes()).toContain('-rank-color');
      expect(wrapper.classes()).toContain('-s');
    });
  });

  describe('when interacting with the button', () => {
    it('should notify when clicked', async () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
        },
      });

      await wrapper.trigger('click');

      expect(wrapper.emitted('click')).toBeTruthy();
      expect(wrapper.emitted('click')?.length).toBe(1);
    });

    it('should prevent interaction when disabled', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          disabled: true,
        },
      });

      expect(wrapper.attributes('disabled')).toBe('');
    });
  });
});
