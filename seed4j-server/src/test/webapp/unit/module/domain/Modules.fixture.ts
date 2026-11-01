import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { Modules } from '@/module/domain/Modules';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { ModulesToApply } from '@/module/domain/ModulesToApply';
import { ModuleToApply } from '@/module/domain/ModuleToApply';
import { Presets } from '@/module/domain/Presets';
import { Project } from '@/module/domain/Project';
import { ModulePropertyValue, ProjectHistory } from '@/module/domain/ProjectHistory';
import { type MockedFunction, vi } from 'vitest';

export interface ModulesRepositoryStub extends ModulesRepository {
  list: MockedFunction<any>;
  landscape: MockedFunction<any>;
  apply: MockedFunction<any>;
  applyAll: MockedFunction<any>;
  history: MockedFunction<any>;
  download: MockedFunction<any>;
  preset: MockedFunction<any>;
}

export const stubModulesRepository = (): ModulesRepositoryStub =>
  ({
    list: vi.fn(),
    landscape: vi.fn(),
    apply: vi.fn(),
    applyAll: vi.fn(),
    history: vi.fn(),
    download: vi.fn(),
    preset: vi.fn(),
  }) as ModulesRepositoryStub;

export const applicationBaseNamePropertyDefinition = (): ModulePropertyDefinition => ({
  type: 'STRING',
  mandatory: true,
  key: 'baseName',
  description: 'Application base name',
  defaultValue: 'seed4j',
  order: -300,
});

export const indentSizePropertyDefinition = (): ModulePropertyDefinition => ({
  type: 'INTEGER',
  mandatory: true,
  key: 'indentSize',
  description: 'Application indent size',
  defaultValue: '2',
  order: -100,
});

export const mandatoryBooleanPropertyDefinitionWithoutDefault = (): ModulePropertyDefinition => ({
  type: 'BOOLEAN',
  mandatory: true,
  key: 'mandatoryBoolean',
  description: 'Test Mandatory boolean',
  order: -50,
});

export const mandatoryBooleanPropertyDefinitionWithDefault = (): ModulePropertyDefinition => ({
  type: 'BOOLEAN',
  mandatory: true,
  key: 'mandatoryBooleanDefault',
  description: 'Test Mandatory boolean with default',
  defaultValue: 'true',
  order: -50,
});

export const mandatoryIntegerPropertyDefinition = (): ModulePropertyDefinition => ({
  type: 'INTEGER',
  mandatory: true,
  key: 'mandatoryInteger',
  description: 'Test Mandatory integer',
  defaultValue: '1337',
  order: -50,
});

export const optionalBooleanPropertyDefinition = (): ModulePropertyDefinition => ({
  type: 'BOOLEAN',
  mandatory: false,
  key: 'optionalBoolean',
  order: -200,
});

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

export const defaultModulesWithNonDefaultProperties = (): Modules =>
  new Modules([
    {
      name: 'Spring',
      modules: [
        {
          slug: moduleSlug('spring-cucumber'),
          description: 'Add cucumber to the application',
          properties: [
            applicationBaseNamePropertyDefinition(),
            mandatoryBooleanPropertyDefinitionWithoutDefault(),
            mandatoryBooleanPropertyDefinitionWithDefault(),
            mandatoryIntegerPropertyDefinition(),
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

export const defaultModuleToApply = (): ModuleToApply => ({
  projectFolder: '/tmp/dummy',
  commit: true,
  parameters: defaultPropertiesToApply(),
});

export const defaultModulesToApply = (): ModulesToApply => ({
  modules: [moduleSlug('init')],
  projectFolder: '/tmp/dummy',
  commit: true,
  parameters: defaultPropertiesToApply(),
});

const defaultPropertiesToApply = () =>
  new Map<string, ModuleParameterType>().set('baseName', 'testproject').set('optionalBoolean', true).set('optionalInteger', 42);

export const defaultProjectHistory = (): ProjectHistory => ({
  modules: [moduleSlug('spring-cucumber')],
  properties: appliedModuleProperties(),
});

export const projectHistoryWithInit = (): ProjectHistory => ({
  modules: [moduleSlug('init'), moduleSlug('prettier')],
  properties: appliedModuleProperties(),
});

export const appliedModuleProperties = (): ModulePropertyValue[] => [{ key: 'baseName', value: 'setbase' }];

export const defaultProject = (): Project => ({
  filename: 'seed4j.zip',
  content: Uint8Array.from([]).buffer,
});

export const defaultPresets = (): Presets => ({
  presets: [
    {
      name: 'init-maven',
      modules: [moduleSlug('init'), moduleSlug('maven')],
    },
    {
      name: 'init-prettier',
      modules: [moduleSlug('application-service-hexagonal-architecture-documentation'), moduleSlug('init'), moduleSlug('prettier')],
    },
    {
      name: 'init-typescript',
      modules: [moduleSlug('init'), moduleSlug('typescript'), moduleSlug('application-service-hexagonal-architecture-documentation')],
    },
  ],
});

export const moduleSlug = (slug: string): ModuleSlug => new ModuleSlug(slug);
