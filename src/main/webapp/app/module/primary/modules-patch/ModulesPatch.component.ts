import { AlertBus } from '@/common/domain/alert/AlertBus';
import { Loader } from '@/loader/primary/Loader';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { defineComponent, inject, onMounted, reactive, ref } from 'vue';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { IconVue } from '@/common/primary/icon';
import { TagFilterVue } from '../tag-filter';
import { ProjectHistory } from '@/module/domain/ProjectHistory';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ComponentModules } from './ComponentModulesPatch';
import { ComponentModule } from './ComponentModulePatch';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModulePropertiesFormVue } from '../module-properties-form';
import { ModulePropertyKey } from '@/module/domain/ModulePropertyKey';
import { ProjectActionsVue } from '../project-actions';
import { ModuleParameter } from '@/module/domain/ModuleParameter';
import { ModuleParametersVue } from '../module-parameters';
import { castValue } from '../PropertyValue';
import { ModuleParametersRepository } from '@/module/domain/ModuleParametersRepository';
import { ModulesPatchLoaderVue } from '../modules-patch-loader';

export default defineComponent({
  name: 'ModulesPatchVue',
  components: { ModuleParametersVue, IconVue, TagFilterVue, ModulePropertiesFormVue, ProjectActionsVue, ModulesPatchLoaderVue },
  setup() {
    const alertBus = inject('alertBus') as AlertBus;
    const modules = inject('modules') as ModulesRepository;
    const projectFolders = inject('projectFolders') as ProjectFoldersRepository;

    const applicationModules = reactive({
      all: Loader.loading<ComponentModules>(),
      displayed: Loader.loading<ComponentModules>(),
    });

    const selectedTag = ref(undefined as string | undefined);
    const operationInProgress = ref(false);
    const selectedModule = ref<ComponentModule>();
    const moduleParameters = inject('moduleParameters') as ModuleParametersRepository;
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

    const moduleClass = (slug: string): string => {
      return selectionClass(slug) + ' ' + applicationClass(slug);
    };

    const selectionClass = (slug: string): string => {
      if (isModuleSelected(slug)) {
        return 'selected';
      }

      return 'not-selected';
    };

    const isModuleSelected = (slug: string): boolean => {
      return slug === selectedModule.value?.slug;
    };

    const applicationClass = (slug: string): string => {
      if (appliedModules.value.includes(slug)) {
        return 'applied';
      }

      return 'not-applied';
    };

    const disabledApplication = (slug: string): boolean => {
      return (
        operationInProgress.value ||
        empty(folderPath.value) ||
        getModule(slug).properties.some(property => property.mandatory && isNotSet(property.key) && empty(property.defaultValue))
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

    const mandatoryProperties = (module: string): ModulePropertyDefinition[] => {
      return getModule(module).properties.filter(property => property.mandatory);
    };

    const optionalProperties = (module: string): ModulePropertyDefinition[] => {
      return getModule(module).properties.filter(property => !property.mandatory);
    };

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

      return selectedTag.value!.toLowerCase() + ' ' + searchedText.toLowerCase();
    };

    const displayedModulesCount = (): number => {
      return applicationModules.displayed.value().modulesCount;
    };

    const totalModulesCount = (): number => {
      return applicationModules.all.value().modulesCount;
    };

    const isTagSelected = (tag: string): boolean => {
      return selectedTag.value === tag;
    };

    const toogleTag = (tag: string): void => {
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

          alertBus.success('Module "' + module + '" applied');
          appliedModules.value.push(module);
        })
        .catch(() => {
          operationInProgress.value = false;

          alertBus.error('Module "' + module + '" not applied');
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

    const unknownProperty = (key: string) => {
      return !moduleParametersValues.value.has(key);
    };

    const appliedModule = (slug: string): boolean => {
      return appliedModules.value.includes(slug);
    };

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
      toogleTag,
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
