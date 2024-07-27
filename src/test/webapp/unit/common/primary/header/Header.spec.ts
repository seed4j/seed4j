import { HeaderVue } from '@/shared/header/infrastructure/primary';
import { mount, VueWrapper } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import { ManagementRepositoryStub, stubLocalManagementRepository } from '../../../module/domain/ManagementRepository.fixture';
import { LocalWindowThemeRepositoryStub, stubLocalWindowThemeRepository } from '../../../module/domain/ThemeRepository.fixture';

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

  return mount(HeaderVue, {
    global: {
      provide: {
        management,
        themeRepository,
      },
      stubs: ['router-link'],
    },
  });
};

const managementRepositoryStubResolves = (): ManagementRepositoryStub => {
  const management = stubLocalManagementRepository();
  management.getInfo.resolves({ git: { build: { version: '1.0.0' } } });

  return management;
};

const managementRepositoryStubReject = (): ManagementRepositoryStub => {
  const management = stubLocalManagementRepository();
  management.getInfo.rejects('managementRepositoryStubReject error');

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
      return mount(HeaderVue, {
        global: {
          provide: {
            management,
            themeRepository,
          },
          stubs: ['router-link'],
        },
      });
    };

    expect(wrap().exists()).toBe(true);
  });
});
