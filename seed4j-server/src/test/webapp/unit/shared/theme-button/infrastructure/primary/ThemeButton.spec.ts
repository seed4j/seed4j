import { GLOBAL_WINDOW, provide } from '@/injections';
import { THEMES_REPOSITORY } from '@/module/application/ModuleProvider';
import { ThemeButtonVue } from '@/shared/theme-button/infrastructure/primary';
import { VueWrapper, mount } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';
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

    const checkbox = wrapper.find('.seed4j-theme-button-toggle');
    expect(themeRepository.get).toHaveBeenCalledOnce();
    expect(themeRepository.choose).toHaveBeenCalledOnce();

    await checkbox.trigger('change');
    expect(themeRepository.choose).toHaveBeenCalledTimes(2);

    await checkbox.trigger('change');
    expect(themeRepository.choose).toHaveBeenCalledTimes(3);
  });
});
