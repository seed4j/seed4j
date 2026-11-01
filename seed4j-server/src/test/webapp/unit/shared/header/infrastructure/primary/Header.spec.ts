import { provide } from '@/injections';
import { MANAGEMENT_REPOSITORY, THEMES_REPOSITORY } from '@/module/application/ModuleProvider';
import { HeaderVue } from '@/shared/header/infrastructure/primary';
import { VueWrapper, mount } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';
import { ManagementRepositoryStub, stubLocalManagementRepository } from '../../../../module/domain/ManagementRepository.fixture';
import { LocalWindowThemeRepositoryStub, stubLocalWindowThemeRepository } from '../../../../module/domain/ThemeRepository.fixture';

interface WrapperOptions {
  management: ManagementRepositoryStub;
  themeRepository: LocalWindowThemeRepositoryStub;
}

const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
  const { management, themeRepository }: WrapperOptions = {
    management: managementRepositoryStubResolves(),
    themeRepository: stubLocalWindowThemeRepository(),
    ...options,
  };

  provide(MANAGEMENT_REPOSITORY, management);
  provide(THEMES_REPOSITORY, themeRepository);

  return mount(HeaderVue, {
    global: {
      stubs: ['router-link'],
    },
  });
};

const managementRepositoryStubResolves = (): ManagementRepositoryStub => {
  const management = stubLocalManagementRepository();
  management.getInfo.mockResolvedValue({
    git: {
      build: {
        version: '1.35.1-SNAPSHOT',
        time: '2025-09-06T02:39:26Z',
      },
      commit: {
        id: {
          describe: '74a5438',
          abbrev: '74a5438',
        },
      },
      branch: 'main',
    },
  });

  return management;
};

const managementRepositoryStubReject = (): ManagementRepositoryStub => {
  const management = stubLocalManagementRepository();
  management.getInfo.mockRejectedValue('managementRepositoryStubReject error');

  return management;
};

describe('Header', () => {
  it('should exist', () => {
    const wrapper = wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should exist when management endpoint is on error', () => {
    const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
      const { management, themeRepository }: WrapperOptions = {
        management: managementRepositoryStubReject(),
        themeRepository: stubLocalWindowThemeRepository(),
        ...options,
      };

      provide(MANAGEMENT_REPOSITORY, management);
      provide(THEMES_REPOSITORY, themeRepository);

      return mount(HeaderVue, {
        global: {
          stubs: ['router-link'],
        },
      });
    };

    expect(wrap().exists()).toBe(true);
  });
});
