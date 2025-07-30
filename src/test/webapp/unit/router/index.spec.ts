import { APPLICATION_LISTENER, CURSOR_UPDATER, GLOBAL_WINDOW, provide } from '@/injections';
import {
  LANDSCAPE_SCROLLER,
  MANAGEMENT_REPOSITORY,
  MODULES_REPOSITORY,
  MODULE_PARAMETERS_REPOSITORY,
  PROJECT_FOLDERS_REPOSITORY,
  THEMES_REPOSITORY,
} from '@/module/application/ModuleProvider';
import { ModuleParametersRepository } from '@/module/domain/ModuleParametersRepository';
import { Modules } from '@/module/domain/Modules';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { BodyCursorUpdater } from '@/module/primary/landscape/BodyCursorUpdater';
import { LandscapeScroller } from '@/module/primary/landscape/LandscapeScroller';
import { AppVue } from '@/root/infrastructure/primary';
import { routes } from '@/router';
import { ALERT_BUS, ALERT_LISTENER } from '@/shared/alert/application/AlertProvider';
import { AlertListener } from '@/shared/alert/domain/AlertListener';
import { ApplicationListener } from '@/shared/alert/infrastructure/primary/ApplicationListener';
import { TIMEOUT } from '@/shared/toast/application/ToastProvider';
import { TimeoutListener } from '@/shared/toast/domain/Timeout';
import { VueWrapper, flushPromises, mount } from '@vue/test-utils';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { Router, createRouter, createWebHistory } from 'vue-router';
import { defaultLandscape } from '../module/domain/landscape/Landscape.fixture';
import { ManagementRepositoryStub, stubLocalManagementRepository } from '../module/domain/ManagementRepository.fixture';
import { ModuleParametersRepositoryStub, stubModuleParametersRepository } from '../module/domain/ModuleParameters.fixture';
import {
  ModulesRepositoryStub,
  applicationBaseNamePropertyDefinition,
  defaultPresets,
  moduleSlug,
  optionalBooleanPropertyDefinition,
  stubModulesRepository,
} from '../module/domain/Modules.fixture';
import { ProjectFoldersRepositoryStub, stubProjectFoldersRepository } from '../module/domain/ProjectFolders.fixture';
import { LocalWindowThemeRepositoryStub, stubLocalWindowThemeRepository } from '../module/domain/ThemeRepository.fixture';
import { stubWindow } from '../module/primary/GlobalWindow.fixture';
import { stubAlertBus } from '../shared/alert/domain/AlertBus.fixture';
import { stubAlertListener } from '../shared/alert/domain/AlertListener.fixture';
import { stubTimeout } from '../shared/toast/domain/Timeout.fixture';

interface ApplicationListenerStub extends ApplicationListener {
  addEventListener: vi.fn;
  removeEventListener: vi.fn;
}

interface BodyCursorUpdaterStub extends BodyCursorUpdater {
  set: vi.fn;
  reset: vi.fn;
}

const stubBodyCursorUpdater = (): BodyCursorUpdaterStub =>
  ({
    set: vi.fn(),
    reset: vi.fn(),
  }) as BodyCursorUpdaterStub;

const stubApplicationListener = (): ApplicationListenerStub => ({
  addEventListener: vi.fn(),
  removeEventListener: vi.fn(),
});

const stubLandscapeScroller = (): LandscapeScroller => ({
  scroll: vi.fn(),
  scrollSmooth: vi.fn(),
  scrollIntoView: vi.fn(),
});

const managementRepositoryStubResolves = (): ManagementRepositoryStub => {
  const management = stubLocalManagementRepository();
  management.getInfo.mockResolvedValue({ git: { build: { version: '1.0.0' } } });

  return management;
};

interface WrapperOptions {
  alertListener: AlertListener;
  toastTimeout: TimeoutListener;
  cursorUpdater: BodyCursorUpdater;
  landscapeScroller: LandscapeScroller;
  modules: ModulesRepository;
  applicationListener: ApplicationListener;
  moduleParameters: ModuleParametersRepository;
  management: ManagementRepositoryStub;
  theme: LocalWindowThemeRepositoryStub;
  projectFolders: ProjectFoldersRepository;
}

const alertBus = stubAlertBus();

let router: Router;
beforeEach(() => {
  router = createRouter({
    history: createWebHistory(),
    routes,
  });
});

const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
  const {
    alertListener,
    toastTimeout,
    applicationListener,
    cursorUpdater,
    landscapeScroller,
    modules,
    moduleParameters,
    management,
    theme,
    projectFolders,
  }: WrapperOptions = {
    alertListener: stubAlertListener(),
    toastTimeout: stubTimeout(),
    cursorUpdater: stubBodyCursorUpdater(),
    landscapeScroller: stubLandscapeScroller(),
    modules: repositoryWithLandscape(),
    applicationListener: stubApplicationListener(),
    moduleParameters: repositoryWithModuleParameters(),
    management: managementRepositoryStubResolves(),
    theme: stubLocalWindowThemeRepository(),
    projectFolders: repositoryWithProjectFolders(),
    ...options,
  };

  provide(ALERT_LISTENER, alertListener);
  provide(TIMEOUT, toastTimeout);
  provide(ALERT_BUS, alertBus);
  provide(APPLICATION_LISTENER, applicationListener);
  provide(CURSOR_UPDATER, cursorUpdater);
  provide(LANDSCAPE_SCROLLER, landscapeScroller);
  provide(MODULES_REPOSITORY, modules);
  provide(MODULE_PARAMETERS_REPOSITORY, moduleParameters);
  provide(GLOBAL_WINDOW, stubWindow());
  provide(PROJECT_FOLDERS_REPOSITORY, repositoryWithProjectFolders());
  provide(MANAGEMENT_REPOSITORY, management);
  provide(THEMES_REPOSITORY, theme);
  provide(PROJECT_FOLDERS_REPOSITORY, projectFolders);

  return mount(AppVue, {
    global: {
      plugins: [router],
    },
  });
};

export const defaultModules = (): Modules =>
  new Modules([
    {
      name: 'Spring',
      modules: [
        {
          slug: moduleSlug('spring-cucumber'),
          description: 'Add cucumber to the application',
          properties: [
            applicationBaseNamePropertyDefinition(),
            optionalBooleanPropertyDefinition(),
            {
              type: 'INTEGER',
              mandatory: false,
              key: 'optionalInteger',
              order: 100,
            },
          ],
          tags: ['server'],
        },
        {
          slug: moduleSlug('banner'),
          description: 'Add a banner to the application',
          properties: [],
          tags: [],
        },
      ],
    },
  ]);

const repositoryWithLandscape = (): ModulesRepositoryStub => {
  const modules = stubModulesRepository();
  modules.landscape.mockResolvedValue(defaultLandscape());
  modules.preset.mockResolvedValue(defaultPresets());

  return modules;
};

const repositoryWithModules = (): ModulesRepositoryStub => {
  const modules = stubModulesRepository();
  modules.list.mockResolvedValue(defaultModules());

  return modules;
};

const repositoryWithProjectFolders = (): ProjectFoldersRepositoryStub => {
  const projectFolders = stubProjectFoldersRepository();
  projectFolders.get.mockResolvedValue('/tmp/seed4j/1234');

  return projectFolders;
};

const repositoryWithModuleParameters = (): ModuleParametersRepositoryStub => {
  const moduleParameters = stubModuleParametersRepository();
  moduleParameters.store.mockResolvedValue(null);
  moduleParameters.storeCurrentFolderPath.mockResolvedValue('');
  moduleParameters.getCurrentFolderPath.mockReturnValue('');
  moduleParameters.get.mockReturnValue(new Map());
  return moduleParameters;
};

describe('Router', () => {
  describe.for([['/'], ['/landscape']])('Navigation on LandscapeVue', url => {
    it(`should navigate on LandscapeVue when the URL is ${url}`, async () => {
      await router.push(url);

      const wrapper = wrap();

      expect(wrapper.html()).toContain('jhlite-landscape-loader');
    });
  });

  describe('Navigation on ModulesVue', () => {
    it('should navigate on ModulesVue when the URL is /patches', async () => {
      await router.push('/patches');

      const modules = repositoryWithModules();
      modules.apply.mockResolvedValue(null);
      const wrapper = wrap({ modules });

      await flushPromises();

      expect(wrapper.html()).toContain('jhipster-modules-patch');
    });
  });
});
