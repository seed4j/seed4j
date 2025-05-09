import { mount, VueWrapper } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

import { GLOBAL_WINDOW, provide } from '@/injections';
import { THEMES_REPOSITORY } from '@/module/application/ModuleProvider';
import { ThemeButtonVue } from '@/shared/theme-button/infrastructure/primary';

import { LocalWindowThemeRepositoryStub, stubLocalWindowThemeRepository } from '../../../../module/domain/ThemeRepository.fixture';
import { stubWindow } from '../../../../module/primary/GlobalWindow.fixture';

interface WrapperOptions {
  themeRepository: LocalWindowThemeRepositoryStub;
}

const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
  const { themeRepository }: WrapperOptions = {
    themeRepository: stubLocalWindowThemeRepository(),
    ...options,
  };

  provide(THEMES_REPOSITORY, themeRepository);
  provide(GLOBAL_WINDOW, stubWindow());

  return mount(ThemeButtonVue);
};

describe('ThemeButton', () => {
  it('should exist', () => {
    const wrapper = wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should switch theme', async () => {
    const themeRepository = stubLocalWindowThemeRepository();
    const wrapper = wrap({ themeRepository });

    const checkbox = wrapper.find('.jhlite-theme-button-toggle');
    expect(themeRepository.get.calledOnce).toBe(true);
    expect(themeRepository.choose.callCount).toBe(1);

    await checkbox.trigger('change');
    expect(themeRepository.choose.callCount).toBe(2);

    checkbox.trigger('change');
    expect(themeRepository.choose.callCount).toBe(3);
  });
});
