import { mount, VueWrapper } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

import { Landscape } from '@/module/domain/landscape/Landscape';
import { RANKS } from '@/module/domain/landscape/ModuleRank';
import { ModuleRankStatistics, toModuleRankStatistics } from '@/module/domain/ModuleRankStatistics';
import { LandscapeRankModuleFilterVue } from '@/module/primary/landscape-rank-module-filter';
import { Optional } from '@/shared/optional/domain/Optional';

import { wrappedElement } from '../../../WrappedElement';
import { initialModule } from '../../domain/landscape/Landscape.fixture';
import { applicationBaseNamePropertyDefinition } from '../../domain/Modules.fixture';

const wrap = (props?: { moduleRankStatistics: ModuleRankStatistics }): VueWrapper => {
  return mount(LandscapeRankModuleFilterVue, {
    props: props || {
      moduleRankStatistics: RANKS.map(rank => ({ rank, quantity: 1 })),
    },
  });
};

describe('LandscapeRankModuleFilterComponent', () => {
  it('should display all rank filters', () => {
    const wrapper = wrap();

    const buttons = wrapper.findAll('[data-testid^="rank-"]');
    expect(buttons).toHaveLength(RANKS.length);
  });

  it('should select rank when clicking on filter button', async () => {
    const wrapper = wrap();

    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));
    await rankDButton.trigger('click');

    expect(wrapper.emitted('selected')).toEqual([[Optional.of(RANKS[0])]]);
  });

  it('should deselect rank when clicking on selected filter button', async () => {
    const wrapper = wrap();

    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));
    await rankDButton.trigger('click');
    await rankDButton.trigger('click');

    expect(wrapper.emitted('selected')).toEqual([[Optional.of(RANKS[0])], [Optional.empty()]]);
  });

  it('should format rank short name correctly', () => {
    const wrapper = wrap();

    const buttons = wrapper.findAll('[data-testid^="short-name-"]');
    RANKS.forEach((rank, index) => {
      expect(buttons[index].text()).toBe(rank.replace('RANK_', ''));
    });
  });

  it('should format rank full name correctly', () => {
    const wrapper = wrap();

    const buttons = wrapper.findAll('[data-testid^="full-name-"]');
    RANKS.forEach((rank, index) => {
      expect(buttons[index].text()).toBe(rank.replace('RANK_', 'RANK '));
    });
  });

  it('should apply selected style to active filter', async () => {
    const wrapper = wrap();

    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));
    await rankDButton.trigger('click');

    expect(rankDButton.classes()).toContain('-active');
  });

  it('should emit selected rank when clicking a filter button', async () => {
    const wrapper = wrap();

    const rankAButton = wrapper.find(wrappedElement('rank-RANK_A-filter'));
    await rankAButton.trigger('click');
    await wrapper.vm.$nextTick();

    expect(wrapper.emitted('selected')).toEqual([[Optional.of(RANKS[3])]]);
  });

  it('should emit undefined when deselecting a rank', async () => {
    const wrapper = wrap();

    const rankSButton = wrapper.find(wrappedElement('rank-RANK_S-filter'));
    await rankSButton.trigger('click');
    await rankSButton.trigger('click');

    expect(wrapper.emitted('selected')).toEqual([[Optional.of(RANKS[4])], [Optional.empty()]]);
  });

  it('should only emit one rank at a time', async () => {
    const wrapper = wrap();

    const rankBButton = wrapper.find(wrappedElement('rank-RANK_B-filter'));
    const rankCButton = wrapper.find(wrappedElement('rank-RANK_C-filter'));
    await rankBButton.trigger('click');
    await rankCButton.trigger('click');

    expect(wrapper.emitted('selected')).toEqual([[Optional.of(RANKS[2])], [Optional.of(RANKS[1])]]);
  });

  it('should display correct description for rank button', () => {
    const wrapper = wrap();

    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));

    expect(rankDButton.attributes('title')).toBe('Experimental or advanced module requiring specific expertise');
  });

  it('should disable rank button without module rank associated', () => {
    const moduleRankStatistics = toModuleRankStatistics(rankEnabledLandscape());
    const wrapper = wrap({ moduleRankStatistics });

    const rankSButton = wrapper.find(wrappedElement('rank-RANK_S-filter'));
    const rankAButton = wrapper.find(wrappedElement('rank-RANK_A-filter'));
    const rankBButton = wrapper.find(wrappedElement('rank-RANK_B-filter'));
    const rankCButton = wrapper.find(wrappedElement('rank-RANK_C-filter'));
    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));

    expect(rankSButton.attributes('disabled')).toBeUndefined();
    expect(rankAButton.attributes('disabled')).toBeUndefined();
    expect(rankBButton.attributes('disabled')).toBeUndefined();
    expect(rankCButton.attributes('disabled')).toBeDefined();
    expect(rankDButton.attributes('disabled')).toBeDefined();
  });

  it('should display the colors associated with each rank when select a rank', async () => {
    const wrapper = wrap();

    const rankAButton = wrapper.find(wrappedElement('rank-RANK_A-filter'));
    await rankAButton.trigger('click');

    const rankColorClasses = {
      RANK_D: '-d',
      RANK_C: '-c',
      RANK_B: '-b',
      RANK_A: '-a',
      RANK_S: '-s',
    };
    for (const rank of RANKS) {
      const rankButton = wrapper.find(wrappedElement(`rank-${rank}-filter`));

      expect(rankButton.classes()).toContain('-rank-color');
      expect(rankButton.classes()).toContain(rankColorClasses[rank]);
    }
  });

  it('should display buttons without colors by default', () => {
    const wrapper = wrap();

    const rankColorClasses = {
      RANK_D: '-d',
      RANK_C: '-c',
      RANK_B: '-b',
      RANK_A: '-a',
      RANK_S: '-s',
    };
    for (const rank of RANKS) {
      const rankButton = wrapper.find(wrappedElement(`rank-${rank}-filter`));

      expect(rankButton.classes()).not.toContain('-rank-color');
      expect(rankButton.classes()).not.toContain(rankColorClasses[rank]);
    }
  });

  it('should display buttons without colors when a selected rank is deselected', async () => {
    const wrapper = wrap();

    const rankAButton = wrapper.find(wrappedElement('rank-RANK_A-filter'));
    await rankAButton.trigger('click');
    await rankAButton.trigger('click');

    const rankColorClasses = {
      RANK_D: '-d',
      RANK_C: '-c',
      RANK_B: '-b',
      RANK_A: '-a',
      RANK_S: '-s',
    };
    for (const rank of RANKS) {
      const rankButton = wrapper.find(wrappedElement(`rank-${rank}-filter`));

      expect(rankButton.classes()).not.toContain('-rank-color');
      expect(rankButton.classes()).not.toContain(rankColorClasses[rank]);
    }
  });

  it('should reduce the attention to the ranks colors which do not match the selected rank', async () => {
    const wrapper = wrap();

    const rankAButton = wrapper.find(wrappedElement('rank-RANK_A-filter'));
    await rankAButton.trigger('click');

    expect(rankAButton.classes()).toContain('-active');
    expect(rankAButton.classes()).toContain('-rank-color');
    expect(rankAButton.classes()).toContain('-a');
    expect(rankAButton.classes()).not.toContain('-reduced-attention');
    for (const rank of RANKS.filter(r => r !== 'RANK_A')) {
      const otherRankButton = wrapper.find(wrappedElement(`rank-${rank}-filter`));
      expect(otherRankButton.classes()).toContain('-reduced-attention');
      expect(otherRankButton.classes()).not.toContain('-active');
    }

    await rankAButton.trigger('click');
    for (const rank of RANKS) {
      const rankButton = wrapper.find(wrappedElement(`rank-${rank}-filter`));
      expect(rankButton.classes()).not.toContain('-reduced-attention');
    }
  });

  const rankEnabledLandscape = (): Landscape =>
    Landscape.initialState([
      {
        elements: [
          initialModule('rank-s', 'Add infinitest filters', [applicationBaseNamePropertyDefinition()], [], 'RANK_S'),
          initialModule('rank-a', 'Add some initial tools', [applicationBaseNamePropertyDefinition()], [], 'RANK_A'),
          initialModule('rank-b', 'Add some initial tools', [applicationBaseNamePropertyDefinition()], [], 'RANK_B'),
        ],
      },
    ]);
});
