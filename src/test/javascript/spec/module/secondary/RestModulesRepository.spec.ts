import { RestModulesRepository } from '@/module/secondary/RestModulesRepository';
import { RestProjectHistory } from '@/module/secondary/RestProjectHistory';
import { RestModulePropertiesDefinitions } from '@/module/secondary/RestModulePropertiesDefinitions';
import { RestModules } from '@/module/secondary/RestModules';
import { dataBackendResponse, stubAxiosHttp } from '../../http/AxiosHttpStub';
import { defaultModules, defaultModulesToApply, defaultModuleToApply, defaultProjectHistory } from '../domain/Modules.fixture';
import { RestLandscape } from '@/module/secondary/RestLandscape';
import { RestLandscapeDependency, RestLandscapeModule } from '@/module/secondary/RestLandscapeModule';
import { RestLandscapeFeature } from '@/module/secondary/RestLandscapeFeature';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { RestModulePropertyDefinition } from '@/module/secondary/RestModulePropertyDefinition';
import { defaultLandscape } from '../domain/landscape/Landscape.fixture';
import { describe, it, expect } from 'vitest';

describe('Rest modules repository', () => {
  it('Should list modules using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.resolves(dataBackendResponse(restModules()));

    const modules = await repository.list();

    expect(modules).toEqual(defaultModules());
  });

  it('Should list module without properties', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.resolves(dataBackendResponse(restModulesWithoutProperties()));

    const modules = await repository.list();

    expect(modules.categories[0].modules[0].properties).toEqual([]);
  });

  it('Should get landscape using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.resolves(dataBackendResponse(restLandscape()));

    const landscape = await repository.landscape();

    expect(landscape).toEqual(defaultLandscape());
  });

  it('Should apply modules using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.post.resolves(dataBackendResponse(null));

    await repository.apply(new ModuleSlug('module'), defaultModuleToApply());

    expect(axiosInstance.post.calledOnce).toBe(true);
  });

  it('Should apply all modules using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.post.resolves(dataBackendResponse(null));

    await repository.applyAll(defaultModulesToApply());

    expect(axiosInstance.post.calledOnce).toBe(true);
  });

  it('Should get empty module history using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.resolves(dataBackendResponse({}));

    const appliedModules = await repository.history('test');

    expect(appliedModules).toEqual({
      modules: [],
      properties: [],
    });
  });

  it('Should get module history using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.resolves(dataBackendResponse(restModuleHistory()));

    const appliedModules = await repository.history('path/to\\project');

    expect(axiosInstance.get.lastCall.args[0]).toBe('/api/projects?path=path/to%5Cproject');
    expect(appliedModules).toEqual(defaultProjectHistory());
  });

  it('Should format project using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.post.resolves(dataBackendResponse(null));

    await repository.format('path/to\\project');

    expect(axiosInstance.post.lastCall.args[0]).toBe('/api/format-project?path=path/to%5Cproject');
    expect(axiosInstance.post.calledOnce).toBe(true);
  });

  it('Should download project using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.resolves({ headers: { 'x-suggested-filename': 'file.zip' }, data: [1, 2, 3] });

    const project = await repository.download('path/to\\project');

    expect(axiosInstance.get.lastCall.args[0]).toBe('/api/projects?path=path/to%5Cproject');
    expect(project.filename).toBe('file.zip');
    expect(project.content).toEqual([1, 2, 3]);
  });

  it('Should fail to download when there is no suggested filename', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestModulesRepository(axiosInstance);
    axiosInstance.get.resolves({ headers: {}, data: [1, 2, 3] });

    await expect(repository.download('path/to/project')).rejects.toEqual(new Error('Impossible to download file without filename'));
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
        landscapeModule('infinitest', 'Add infinitest filters', applicationBaseNameProperties()),
        landscapeModule('init', 'Add some initial tools', applicationBaseNameProperties()),
        landscapeModule('init-props', 'Add some initial tools with extra properties', initPropsProperties()),
        landscapeModule('prettier', 'Add prettier', applicationBaseNameProperties()),
      ],
    },
    {
      elements: [
        landscapeFeature('client', [
          landscapeModule('vue', 'Add vue', emptyProperties(), [moduleDependency('init')]),
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
        landscapeModule('java-base', 'Add base java classes', emptyProperties(), [featureDependency('java-build-tools')]),
        landscapeModule('spring-boot', 'Add spring boot core', emptyProperties(), [featureDependency('java-build-tools')]),
        landscapeFeature('ci', [
          landscapeModule('gitlab-maven', 'Add simple gitlab ci for maven', emptyProperties(), [moduleDependency('maven')]),
          landscapeModule('gitlab-gradle', 'Add simple gitlab ci for gradle', emptyProperties(), [moduleDependency('gradle')]),
        ]),
      ],
    },
    {
      elements: [
        landscapeFeature('jpa', [landscapeModule('postgresql', 'Add PostGreSQL', emptyProperties(), [moduleDependency('spring-boot')])]),
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
        landscapeModule('dummy-feature', 'Add dummy feature', emptyProperties(), [
          featureDependency('spring-mvc'),
          moduleDependency('bean-validation-test'),
        ]),
        landscapeModule('liquibase', 'Add liquibase', emptyProperties(), [featureDependency('jpa')]),
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
): RestLandscapeModule => ({
  type: 'MODULE',
  slug,
  operation,
  properties,
  dependencies,
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

const appliedModuleProperties = (): {} => ({ baseName: 'settedbase' });

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
  defaultValue: 'jhipster',
  order: -300,
});

const optionalBooleanProperty = (): RestModulePropertyDefinition => ({
  type: 'BOOLEAN',
  mandatory: false,
  key: 'optionalBoolean',
  order: -200,
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
