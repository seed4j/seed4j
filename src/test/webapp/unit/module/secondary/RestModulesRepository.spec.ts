import { ModuleRank } from '@/module/domain/landscape/ModuleRank';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { Presets } from '@/module/domain/Presets';
import { RestLandscape } from '@/module/secondary/RestLandscape';
import { RestLandscapeFeature } from '@/module/secondary/RestLandscapeFeature';
import { RestLandscapeDependency, RestLandscapeModule } from '@/module/secondary/RestLandscapeModule';
import { RestModulePropertiesDefinitions } from '@/module/secondary/RestModulePropertiesDefinitions';
import { RestModulePropertyDefinition } from '@/module/secondary/RestModulePropertyDefinition';
import { RestModules } from '@/module/secondary/RestModules';
import { RestModulesRepository } from '@/module/secondary/RestModulesRepository';
import { RestProjectHistory } from '@/module/secondary/RestProjectHistory';
import { describe, expect, it } from 'vitest';
import { dataBackendResponse, stubAxiosHttp } from '../../shared/http/infrastructure/secondary/AxiosHttpStub';
import { defaultLandscape } from '../domain/landscape/Landscape.fixture';
import {
  defaultModuleToApply,
  defaultModules,
  defaultModulesToApply,
  defaultPresets,
  defaultProjectHistory,
  moduleSlug,
} from '../domain/Modules.fixture';

describe('Rest modules repository', () => {
  it('should list modules using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.mockResolvedValue(dataBackendResponse(restModules()));

    const modules = await repository.list();

    expect(modules).toEqual(defaultModules());
  });

  it('should list module without properties', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.mockResolvedValue(dataBackendResponse(restModulesWithoutProperties()));

    const modules = await repository.list();

    expect(modules.categories[0].modules[0].properties).toEqual([]);
  });

  it('should get landscape using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.mockResolvedValue(dataBackendResponse(restLandscape()));

    const landscape = await repository.landscape();

    expect(landscape).toEqual(defaultLandscape());
  });

  it('should apply modules using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.post.mockResolvedValue(dataBackendResponse(null));

    await repository.apply(new ModuleSlug('module'), defaultModuleToApply());

    expect(axiosInstance.post).toHaveBeenCalledOnce();
  });

  it('should apply all modules using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.post.mockResolvedValue(dataBackendResponse(null));

    await repository.applyAll(defaultModulesToApply());

    expect(axiosInstance.post).toHaveBeenCalled();
  });

  it('should get empty module history using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.mockResolvedValue(dataBackendResponse({}));

    const appliedModules = await repository.history('test');

    expect(appliedModules).toEqual({
      modules: [],
      properties: [],
    });
  });

  it('should get module history using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.mockResolvedValue(dataBackendResponse(restModuleHistory()));

    const appliedModules = await repository.history('path/to\\project');

    expect(axiosInstance.get).toHaveBeenCalledWith('/api/projects?path=path/to%5Cproject');
    expect(appliedModules).toEqual(defaultProjectHistory());
  });

  it('should download project using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.mockResolvedValue({ headers: { 'x-suggested-filename': 'file.zip' }, data: [1, 2, 3] });

    const project = await repository.download('path/to\\project');

    expect(axiosInstance.get).toHaveBeenCalledWith('/api/projects?path=path/to%5Cproject', {
      responseType: 'blob',
      headers: { Accept: 'application/octet-stream' },
    });
    expect(project.filename).toBe('file.zip');
    expect(project.content).toEqual([1, 2, 3]);
  });

  it('should fail to download when there is no suggested filename', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.mockResolvedValue({ headers: {}, data: [1, 2, 3] });

    await expect(repository.download('path/to/project')).rejects.toEqual(new Error('Impossible to download file without filename'));
  });

  it('should get preset configurations using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.mockResolvedValue(dataBackendResponse(restPresets()));

    const presetConfigurations = await repository.preset();

    expect(presetConfigurations).toEqual(defaultPresets());
  });
});

const restModules = (): RestModules => ({
  categories: [
    {
      name: 'Spring',
      modules: [
        {
          slug: 'spring-cucumber',
          description: 'Add cucumber to the application',
          properties: restModuleProperties(),
          tags: ['server'],
        },
        {
          slug: 'banner',
          description: 'Add a banner to the application',
        },
      ],
    },
  ],
});

const restModulesWithoutProperties = (): RestModules => ({
  categories: [
    {
      name: 'Spring',
      modules: [
        {
          slug: 'spring-cucumber',
          description: 'Add cucumber to the application',
        },
      ],
    },
  ],
});

const restLandscape = (): RestLandscape => ({
  levels: [
    {
      elements: [
        landscapeModule('infinitest', 'Add infinitest filters', applicationBaseNameProperties(), [], 'RANK_S'),
        landscapeModule('init', 'Add some initial tools', applicationBaseNameProperties(), [], 'RANK_S'),
        landscapeModule('init-props', 'Add some initial tools with extra properties', initPropsProperties()),
        landscapeModule('prettier', 'Add prettier', applicationBaseNameProperties()),
      ],
    },
    {
      elements: [
        landscapeFeature('client', [
          landscapeModule('vue', 'Add vue', emptyProperties(), [moduleDependency('init')], 'RANK_S'),
          landscapeModule('react', 'Add react', emptyProperties(), [moduleDependency('init')]),
          landscapeModule('angular', 'Add angular', emptyProperties(), [moduleDependency('init')]),
        ]),
        landscapeFeature('java-build-tools', [
          landscapeModule('maven', 'Add maven', optionalBooleanProperties(), [moduleDependency('init')]),
          landscapeModule('gradle', 'Add gradle', emptyProperties(), [moduleDependency('init')]),
        ]),
      ],
    },
    {
      elements: [
        landscapeModule('java-base', 'Add base java classes', emptyProperties(), [featureDependency('java-build-tools')], 'RANK_S'),
        landscapeModule('spring-boot', 'Add spring boot core', emptyProperties(), [featureDependency('java-build-tools')], 'RANK_B'),
        landscapeFeature('ci', [
          landscapeModule('gitlab-maven', 'Add simple gitlab ci for maven', emptyProperties(), [moduleDependency('maven')], 'RANK_S'),
          landscapeModule('gitlab-gradle', 'Add simple gitlab ci for gradle', emptyProperties(), [moduleDependency('gradle')], 'RANK_S'),
        ]),
      ],
    },
    {
      elements: [
        landscapeFeature('jpa', [
          landscapeModule('postgresql', 'Add PostgreSQL', emptyProperties(), [moduleDependency('spring-boot')], 'RANK_C'),
        ]),
        landscapeFeature('spring-mvc', [
          landscapeModule('spring-boot-tomcat', 'Add Tomcat', emptyProperties(), [moduleDependency('spring-boot')]),
          landscapeModule('spring-boot-undertow', 'Add Undertow', emptyProperties(), [moduleDependency('spring-boot')]),
        ]),
        landscapeModule('bean-validation-test', 'Add bean validation test tools', emptyProperties(), [moduleDependency('spring-boot')]),
        landscapeModule('build', 'Add build information', emptyProperties(), [featureDependency('ci')]),
      ],
    },
    {
      elements: [
        landscapeModule('sample-feature', 'Add sample feature', emptyProperties(), [
          featureDependency('spring-mvc'),
          moduleDependency('bean-validation-test'),
        ]),
        landscapeModule('liquibase', 'Add liquibase', emptyProperties(), [featureDependency('jpa')], 'RANK_A'),
      ],
    },
  ],
});

const emptyProperties = (): RestModulePropertiesDefinitions => ({ definitions: [] });

const landscapeModule = (
  slug: string,
  operation: string,
  properties: RestModulePropertiesDefinitions,
  dependencies?: RestLandscapeDependency[],
  rank: ModuleRank = 'RANK_D',
): RestLandscapeModule => ({
  type: 'MODULE',
  slug,
  operation,
  properties,
  dependencies,
  rank,
});

const landscapeFeature = (slug: string, modules: RestLandscapeModule[]): RestLandscapeFeature => ({
  type: 'FEATURE',
  slug,
  modules,
});

const moduleDependency = (slug: string): RestLandscapeDependency => ({ type: 'MODULE', slug });
const featureDependency = (slug: string): RestLandscapeDependency => ({ type: 'FEATURE', slug });

const restModuleHistory = (): RestProjectHistory => ({
  modules: [{ slug: 'spring-cucumber' }],
  properties: appliedModuleProperties(),
});

const appliedModuleProperties = (): {} => ({ baseName: 'setbase' });

const restModuleProperties = (): RestModulePropertiesDefinitions => ({
  definitions: [
    applicationBaseNameProperty(),
    {
      type: 'BOOLEAN',
      mandatory: false,
      key: 'optionalBoolean',
      order: -200,
    },
    {
      type: 'INTEGER',
      mandatory: false,
      key: 'optionalInteger',
      order: 100,
    },
  ],
});

const applicationBaseNameProperties = (): RestModulePropertiesDefinitions => ({
  definitions: [applicationBaseNameProperty()],
});

const initPropsProperties = (): RestModulePropertiesDefinitions => ({
  definitions: [
    applicationBaseNameProperty(),
    indentSizePropertyDefinition(),
    mandatoryBooleanPropertyDefinition(),
    mandatoryBooleanPropertyDefinitionWithDefault(),
  ],
});

const optionalBooleanProperties = (): RestModulePropertiesDefinitions => ({
  definitions: [optionalBooleanProperty()],
});

const applicationBaseNameProperty = (): RestModulePropertyDefinition => ({
  type: 'STRING',
  mandatory: true,
  key: 'baseName',
  description: 'Application base name',
  defaultValue: 'seed4j',
  order: -300,
});

const optionalBooleanProperty = (): RestModulePropertyDefinition => ({
  type: 'BOOLEAN',
  mandatory: false,
  key: 'optionalBoolean',
  order: -200,
});

const restPresets = (): Presets => ({
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

export const indentSizePropertyDefinition = (): RestModulePropertyDefinition => ({
  type: 'INTEGER',
  mandatory: true,
  key: 'indentSize',
  description: 'Application indent size',
  defaultValue: '2',
  order: -100,
});

export const mandatoryBooleanPropertyDefinition = (): RestModulePropertyDefinition => ({
  type: 'BOOLEAN',
  mandatory: true,
  key: 'mandatoryBoolean',
  description: 'Test Mandatory boolean',
  order: -50,
});

export const mandatoryBooleanPropertyDefinitionWithDefault = (): RestModulePropertyDefinition => ({
  type: 'BOOLEAN',
  mandatory: true,
  key: 'mandatoryBooleanDefault',
  description: 'Test Mandatory boolean with default',
  defaultValue: 'true',
  order: -50,
});
