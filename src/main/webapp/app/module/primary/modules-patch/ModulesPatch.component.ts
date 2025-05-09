import { defineComponent, onMounted, reactive, ref } from 'vue';

import { inject } from '@/injections';
import { MODULE_PARAMETERS_REPOSITORY, MODULES_REPOSITORY, PROJECT_FOLDERS_REPOSITORY } from '@/module/application/ModuleProvider';
import { ModuleParameter } from '@/module/domain/ModuleParameter';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModulePropertyKey } from '@/module/domain/ModulePropertyKey';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ProjectHistory } from '@/module/domain/ProjectHistory';
import { ALERT_BUS } from '@/shared/alert/application/AlertProvider';
import { IconVue } from '@/shared/icon/infrastructure/primary';
import { Loader } from '@/shared/loader/infrastructure/primary/Loader';

import { ModuleParametersVue } from '../module-parameters';
import { ModulePropertiesFormVue } from '../module-properties-form';
import { ModulesPatchLoaderVue } from '../modules-patch-loader';
import { ProjectActionsVue } from '../project-actions';
import { castValue } from '../PropertyValue';
import { TagFilterVue } from '../tag-filter';
import { ComponentModule } from './ComponentModulePatch';
import { ComponentModules } from './ComponentModulesPatch';

export default defineComponent({
  name: 'ModulesPatchVue',
  components: {
    ModuleParametersVue,
    IconVue,
    TagFilterVue,
    ModulePropertiesFormVue,
    ProjectActionsVue,
    ModulesPatchLoaderVue,
  },
  setup() {
    const alertBus = inject(ALERT_BUS);
    const modules = inject(MODULES_REPOSITORY);
    const projectFolders = inject(PROJECT_FOLDERS_REPOSITORY);
    const moduleParameters = inject(MODULE_PARAMETERS_REPOSITORY);

    const applicationModules = reactive({
      all: Loader.loading<ComponentModules>(),
      displayed: Loader.loading<ComponentModules>(),
    });
    const selectedTag = ref(undefined as string | undefined);
    const operationInProgress = ref(false);
    const selectedModule = ref<ComponentModule>();
    const folderPath = ref(moduleParameters.getCurrentFolderPath());
    const moduleParametersValues = ref(moduleParameters.get(folderPath.value));
    const commitModule = ref(true);
    const appliedModules = ref([] as string[]);
    let searchedText = '';

    onMounted(() => {
      modules
        .list()
        .then(response => {
          applicationModules.all.loaded(ComponentModules.fromModules(response));
          applicationModules.displayed.loaded(ComponentModules.fromModules(response));
        })
        .catch(error => console.error(error));
      loadProjectFolders();
    });

    const loadProjectFolders = (): void => {
      if (folderPath.value.length === 0) {
        projectFolders
          .get()
          .then(projectFolder => {
            folderPath.value = projectFolder;
            moduleParametersValues.value = moduleParameters.get(folderPath.value);
          })
          .catch(error => console.error(error));
      }
    };

    const operationStarted = (): void => {
      operationInProgress.value = true;
    };

    const operationEnded = (): void => {
      operationInProgress.value = false;
    };

    const toggleModule = (slug: string): void => {
      const clickedModule = getModule(slug);

      if (selectedModule.value === clickedModule) {
        selectedModule.value = undefined;
      } else {
        selectedModule.value = clickedModule;
      }
    };

    const moduleClass = (slug: string): string => `${selectionClass(slug)} ${applicationClass(slug)}`;

    const selectionClass = (slug: string): string => {
      if (isModuleSelected(slug)) {
        return 'selected';
      }

      return 'not-selected';
    };

    const isModuleSelected = (slug: string): boolean => slug === selectedModule.value?.slug;

    const applicationClass = (slug: string): string => {
      if (appliedModules.value.includes(slug)) {
        return 'applied';
      }

      return 'not-applied';
    };

    const disabledApplication = (slug: string): boolean => {
      return (
        operationInProgress.value
        || empty(folderPath.value)
        || getModule(slug).properties.some(property => property.mandatory && isNotSet(property.key) && empty(property.defaultValue))
      );
    };

    const isNotSet = (propertyKey: string): boolean => {
      const value = moduleParametersValues.value.get(propertyKey);

      if (typeof value === 'string') {
        return empty(value);
      }

      return value === undefined;
    };

    const selectedModuleProperties = (): ModulePropertyDefinition[] => {
      const module = selectedModule.value;

      if (module === undefined) {
        return [];
      }

      return module.properties;
    };

    const updateModuleCommit = (commit: boolean): void => {
      commitModule.value = commit;
    };

    const updateFolderPath = (path: string): void => {
      folderPath.value = path;
      moduleParametersValues.value = moduleParameters.get(folderPath.value);
    };

    const updateProperty = (property: ModuleParameter): void => {
      moduleParametersValues.value.set(property.key, property.value);
      moduleParameters.store(folderPath.value, moduleParametersValues.value);
    };

    const deleteProperty = (key: ModulePropertyKey): void => {
      moduleParametersValues.value.delete(key);
      moduleParameters.store(folderPath.value, moduleParametersValues.value);
    };

    const mandatoryProperties = (module: string): ModulePropertyDefinition[] =>
      getModule(module).properties.filter(property => property.mandatory);

    const optionalProperties = (module: string): ModulePropertyDefinition[] =>
      getModule(module).properties.filter(property => !property.mandatory);

    const getModule = (slug: string): ComponentModule => {
      return applicationModules.all
        .value()
        .categories.flatMap(category => category.modules)
        .find(module => module.slug === slug)!;
    };

    const updateSearch = (filter: string): void => {
      searchedText = filter;

      updateFilter();
    };

    const updateFilter = (): void => {
      const search = textToSearch();

      if (search === undefined) {
        applicationModules.displayed.loaded(applicationModules.all.value());
      } else {
        applicationModules.displayed.loaded(applicationModules.all.value().filtered(search));
      }
    };

    const textToSearch = (): string | undefined => {
      if (empty(searchedText) && empty(selectedTag.value)) {
        return undefined;
      }

      if (empty(searchedText)) {
        return selectedTag.value!.toLowerCase();
      }

      if (empty(selectedTag.value)) {
        return searchedText.toLowerCase();
      }

      return `${selectedTag.value!.toLowerCase()} ${searchedText.toLowerCase()}`;
    };

    const displayedModulesCount = (): number => applicationModules.displayed.value().modulesCount;

    const totalModulesCount = (): number => applicationModules.all.value().modulesCount;

    const isTagSelected = (tag: string): boolean => selectedTag.value === tag;

    const toggleTag = (tag: string): void => {
      if (selectedTag.value === tag) {
        selectedTag.value = undefined;
      } else {
        selectedTag.value = tag;
      }

      updateFilter();
    };

    const applyModule = (module: string): void => {
      operationInProgress.value = true;

      selectedModuleProperties().forEach(property => {
        if (unknownProperty(property.key) && property.defaultValue) {
          updateProperty({ key: property.key, value: castValue(property.type, property.defaultValue) });
        }
      });

      modules
        .apply(new ModuleSlug(module), {
          projectFolder: folderPath.value,
          commit: commitModule.value,
          parameters: moduleParametersValues.value,
        })
        .then(() => {
          operationInProgress.value = false;

          alertBus.success(`Module "${module}" applied`);
          appliedModules.value.push(module);
        })
        .catch(() => {
          operationInProgress.value = false;

          alertBus.error(`Module "${module}" not applied`);
        });
    };

    const projectFolderUpdated = (): void => {
      modules
        .history(folderPath.value)
        .then(projectHistory => loadProjectHistory(projectHistory))
        .catch(() => (appliedModules.value = []));
    };

    const loadProjectHistory = (projectHistory: ProjectHistory): void => {
      appliedModules.value = projectHistory.modules.map(module => module.get());

      projectHistory.properties.forEach(property => {
        if (unknownProperty(property.key)) {
          moduleParametersValues.value.set(property.key, property.value);
        }
      });
      moduleParameters.store(folderPath.value, moduleParametersValues.value);
    };

    const unknownProperty = (key: string) => !moduleParametersValues.value.has(key);

    const appliedModule = (slug: string): boolean => appliedModules.value.includes(slug);

    return {
      content: applicationModules.displayed,
      toggleModule,
      isModuleSelected,
      moduleClass,
      selectedModule,
      disabledApplication,
      mandatoryProperties,
      optionalProperties,
      moduleParametersValues,
      displayedModulesCount,
      totalModulesCount,
      isTagSelected,
      toggleTag,
      updateSearch,
      applyModule,
      projectFolderUpdated,
      appliedModule,
      updateModuleCommit,
      updateFolderPath,
      updateProperty,
      deleteProperty,
      selectedModuleProperties,
      folderPath,
      operationStarted,
      operationEnded,
    };
  },
});

const empty = (value: string | undefined): boolean => {
  if (value === undefined) {
    return true;
  }

  return value.trim().length === 0;
};
