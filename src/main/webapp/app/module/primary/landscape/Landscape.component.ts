import { ApplicationListener } from '@/common/primary/applicationlistener/ApplicationListener';
import { Loader } from '@/loader/primary/Loader';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { defineComponent, inject, nextTick, onBeforeUnmount, onMounted, Ref, ref } from 'vue';
import { LandscapeModuleVue } from '../landscape-module';
import { buildConnector, LandscapeConnector } from './LandscapeConnector';
import { DisplayMode } from './DisplayMode';
import { emptyLandscapeSize, LandscapeConnectorsSize } from './LandscapeConnectorsSize';
import { ModulePropertiesFormVue } from '../module-properties-form';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { ProjectHistory } from '@/module/domain/ProjectHistory';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { ProjectActionsVue } from '../project-actions';
import { castValue, empty } from '../PropertyValue';
import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModuleParameter } from '@/module/domain/ModuleParameter';
import { Landscape } from '@/module/domain/landscape/Landscape';
import { IconVue } from '@/common/primary/icon';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { LandscapeLevel } from '@/module/domain/landscape/LandscapeLevel';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { LandscapeSelectionElement } from '@/module/domain/landscape/LandscapeSelectionElement';
import { LandscapeElement } from '@/module/domain/landscape/LandscapeElement';
import { LandscapeFeature } from '@/module/domain/landscape/LandscapeFeature';
import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';
import { LandscapeFeatureSlug } from '@/module/domain/landscape/LandscapeFeatureSlug';
import { BodyCursorUpdater } from '@/common/primary/cursor/BodyCursorUpdater';
import { LandscapeScroller } from '@/module/primary/landscape/LandscapeScroller';

export default defineComponent({
  name: 'LandscapeVue',
  components: { LandscapeModuleVue, ModulePropertiesFormVue, ProjectActionsVue, IconVue },
  setup() {
    const applicationListener = inject('applicationListener') as ApplicationListener;
    const alertBus = inject('alertBus') as AlertBus;
    const cursorUpdater = inject('cursorUpdater') as BodyCursorUpdater;
    const landscapeScroller = inject('landscapeScroller') as LandscapeScroller;
    const modules = inject('modules') as ModulesRepository;
    const projectFolders = inject('projectFolders') as ProjectFoldersRepository;

    const selectedMode = ref<DisplayMode>('COMPACTED');

    const landscape = ref(Loader.loading<Landscape>());
    const levels = ref(Loader.loading<LandscapeLevel[]>());

    const landscapeContainer = ref<HTMLElement>();
    const landscapeConnectors = ref<LandscapeConnector[]>([]);
    const landscapeSize = ref<LandscapeConnectorsSize>(emptyLandscapeSize());
    const landscapeElements = ref(new Map<string, HTMLElement>());

    const emphasizedModule = ref<ModuleSlug>();

    const folderPath = ref('');
    const valuatedModuleParameters = ref(new Map<string, ModuleParameterType>());

    let commitModule = true;

    const isMoving: Ref<boolean> = ref(false);
    const startX: Ref<number> = ref(0);
    const startY: Ref<number> = ref(0);
    const currentScrollX: Ref<number> = ref(0);
    const currentScrollY: Ref<number> = ref(0);

    const operationInProgress = ref(false);

    onMounted(() => {
      modules.landscape().then(response => loadLandscape(response));
      projectFolders.get().then(projectFolder => (folderPath.value = projectFolder));
    });

    const startGrabbing = (mouseEvent: MouseEvent): void => {
      if (mouseEvent.preventDefault) {
        mouseEvent.preventDefault();
      }
      isMoving.value = true;
      startX.value = mouseEvent.clientX;
      startY.value = mouseEvent.clientY;
      const rect = landscapeContainer.value;
      currentScrollX.value = rect?.scrollLeft || 0;
      currentScrollY.value = rect?.scrollTop || 0;
      cursorUpdater.set('grabbing');
    };

    const stopGrabbing = (): void => {
      isMoving.value = false;
      cursorUpdater.reset();
    };

    const grabbing = (mouseEvent: MouseEvent): void => {
      if (!isMoving.value) {
        return;
      }
      const scrollX = currentScrollX.value + (startX.value - mouseEvent.clientX);
      const scrollY = currentScrollY.value + (startY.value - mouseEvent.clientY);
      landscapeScroller.scroll(landscapeContainer.value!, scrollX, scrollY);
    };

    const loadLandscape = (response: Landscape): void => {
      landscape.value.loaded(response);
      levels.value.loaded(response.standaloneLevels());

      nextTick(() => {
        updateConnectors();
      });

      applicationListener.addEventListener('resize', updateConnectors);
    };

    onBeforeUnmount(() => {
      applicationListener.removeEventListener('resize', updateConnectors);
    });

    const updateConnectors = (): void => {
      landscapeConnectors.value = levels.value
        .value()
        .flatMap(level => level.elements)
        .flatMap(element => element.allModules())
        .flatMap(toConnectors);

      landscapeSize.value = buildConnectorsSize();
    };

    const toConnectors = (module: LandscapeModule): LandscapeConnector[] => {
      const dependantElement = landscapeElements.value.get(module.slug().get())!;

      return module.dependencies().flatMap(dependency =>
        buildConnector({
          dependantElement,
          dependantElementSlug: module.slug(),
          dependencyElement: landscapeElements.value.get(dependency.get())!,
          dependencyElementSlug: dependency,
        })
      );
    };

    const buildConnectorsSize = (): LandscapeConnectorsSize => ({
      width: Math.max.apply(
        null,
        landscapeConnectors.value.flatMap(connector => connector.positions).map(position => position.x)
      ),
      height: Math.max.apply(
        null,
        landscapeConnectors.value.flatMap(connector => connector.positions).map(position => position.y)
      ),
    });

    const isFeature = (element: LandscapeElement): boolean => {
      return element instanceof LandscapeFeature;
    };

    const modeSwitchClass = (mode: DisplayMode): string => {
      if (selectedMode.value === mode) {
        return '-selected';
      }

      return '-not-selected';
    };

    const selectMode = (mode: DisplayMode): void => {
      selectedMode.value = mode;

      nextTick(() => {
        updateConnectors();
      });
    };

    const elementFlavor = (module: LandscapeElementId): string => {
      return (
        operationInProgressClass() +
        selectionHighlightClass(module) +
        unselectionHighlightClass(module) +
        selectionClass(module) +
        applicationClass(module) +
        flavorClass()
      );
    };

    const operationInProgressClass = (): string => {
      if (operationInProgress.value) {
        return ' -not-selectable';
      }

      return '';
    };

    const toHighlightSelectionClass = (selection: LandscapeSelectionElement): string => {
      if (selection.selectable) {
        return ' -selectable-highlighted';
      }

      return ' -not-selectable-highlighted';
    };

    const selectionHighlightClass = (module: LandscapeElementId): string => {
      if (emphasizedModule.value === undefined) {
        return '';
      }

      return landscapeValue().selectionTreeFor(emphasizedModule.value).find(module).map(toHighlightSelectionClass).orElse('');
    };

    const unselectionHighlightClass = (element: LandscapeElementId): string => {
      if (isUnselectedByEmphasizedModule(element)) {
        return ' -highlighted-unselection';
      }

      return '';
    };

    const isUnselectedByEmphasizedModule = (element: LandscapeElementId): boolean => {
      if (emphasizedModule.value === undefined) {
        return false;
      }

      return landscapeValue().unselectionTreeFor(emphasizedModule.value).includes(element);
    };

    const selectionClass = (element: LandscapeElementId): string => {
      if (element instanceof LandscapeFeatureSlug) {
        return '';
      }

      if (isSelected(element)) {
        return ' -selected';
      }

      if (isSelectable(element)) {
        return ' -selectable';
      }

      return ' -not-selectable';
    };

    const applicationClass = (element: LandscapeElementId): string => {
      if (element instanceof LandscapeFeatureSlug) {
        return '';
      }

      if (landscapeValue().isApplied(element)) {
        return ' -applied';
      }

      return '';
    };

    const flavorClass = (): string => {
      return ' ' + modeClass();
    };

    const modeClass = (): string => {
      switch (selectedMode.value) {
        case 'COMPACTED':
          return '-compacted';
        case 'EXTENDED':
          return '-extended';
      }
    };

    const emphasizeModule = (module: ModuleSlug): void => {
      emphasizedModule.value = module;
    };

    const deEmphasizeModule = (): void => {
      emphasizedModule.value = undefined;
    };

    const isSelected = (element: LandscapeElementId): boolean => {
      return landscapeValue().isSelected(element);
    };

    const toggleModule = (module: ModuleSlug): void => {
      landscape.value.loaded(landscapeValue().toggle(module));
    };

    const isSelectable = (module: ModuleSlug): boolean => {
      return landscapeValue().isSelectable(module);
    };

    const disabledNewApplication = (): boolean => {
      return disabledApplication() || landscapeValue().noNotAppliedSelectedModule();
    };

    const disabledAllApplication = (): boolean => {
      return disabledApplication();
    };

    const disabledApplication = (): boolean => {
      if (operationInProgress.value) {
        return true;
      }

      if (folderPath.value.length === 0) {
        return true;
      }

      if (landscapeValue().noSelectedModule()) {
        return true;
      }

      if (missingMandatoryProperty()) {
        return true;
      }

      return false;
    };

    const selectedNewModulesCount = (): number => {
      return landscapeValue().notAppliedSelectedModulesCount();
    };

    const selectedModulesCount = (): number => {
      return landscapeValue().selectedModulesCount();
    };

    const missingMandatoryProperty = () => {
      return selectedModulesProperties().some(
        property => property.mandatory && empty(valuatedModuleParameters.value.get(property.key)) && empty(property.defaultValue)
      );
    };

    const selectedModulesProperties = (): ModulePropertyDefinition[] => {
      return landscapeValue().selectedModulesProperties();
    };

    const updateModuleCommit = (commit: boolean) => {
      commitModule = commit;
    };

    const updateFolderPath = (path: string): void => {
      folderPath.value = path;
    };

    const projectFolderUpdated = (): void => {
      modules
        .history(folderPath.value)
        .then(projectHistory => loadProjectHistory(projectHistory))
        .catch(() => {
          //Nothing to do
        });
    };

    const loadProjectHistory = (projectHistory: ProjectHistory): void => {
      landscape.value.loaded(landscapeValue().resetAppliedModules(projectHistory.modules));

      projectHistory.properties.forEach(property => {
        if (unknownProperty(property.key)) {
          valuatedModuleParameters.value.set(property.key, property.value);
        }
      });
    };

    const unknownProperty = (key: string) => {
      return !valuatedModuleParameters.value.has(key);
    };

    const updateProperty = (property: ModuleParameter): void => {
      valuatedModuleParameters.value.set(property.key, property.value);
    };

    const deleteProperty = (key: string): void => {
      valuatedModuleParameters.value.delete(key);
    };

    const applyNewModules = (): void => {
      applyModules(landscapeValue().notAppliedSelectedModules());
    };

    const applyAllModules = (): void => {
      applyModules(landscapeValue().selectedModules());
    };

    const applyModules = (modulesToApply: ModuleSlug[]): void => {
      operationStarted();

      selectedModulesProperties().forEach(property => {
        if (unknownProperty(property.key) && property.defaultValue) {
          updateProperty({ key: property.key, value: castValue(property.type, property.defaultValue) });
        }
      });

      modules
        .applyAll({
          modules: modulesToApply,
          projectFolder: folderPath.value,
          commit: commitModule,
          parameters: valuatedModuleParameters.value,
        })
        .then(() => {
          operationEnded();
          landscape.value.loaded(landscapeValue().appliedModules(modulesToApply));

          alertBus.success('Modules applied');
        })
        .catch(() => {
          operationEnded();

          alertBus.error('Modules not applied');
        });
    };

    const landscapeValue = (): Landscape => {
      return landscape.value.value();
    };

    const operationStarted = (): void => {
      operationInProgress.value = true;
    };

    const operationEnded = (): void => {
      operationInProgress.value = false;
    };

    const isApplied = (moduleId: string): boolean => {
      return landscapeValue().isApplied(new ModuleSlug(moduleId));
    };

    return {
      levels,
      isFeature,
      landscapeConnectors,
      landscapeSize,
      landscapeElements,
      landscapeContainer,
      modeSwitchClass,
      selectMode,
      modeClass,
      emphasizeModule,
      deEmphasizeModule,
      elementFlavor,
      toggleModule,
      disabledNewApplication,
      disabledAllApplication,
      selectedNewModulesCount,
      selectedModulesCount,
      folderPath,
      selectedModulesProperties,
      valuatedModuleParameters,
      updateModuleCommit,
      updateFolderPath,
      projectFolderUpdated,
      updateProperty,
      deleteProperty,
      applyAllModules,
      applyNewModules,
      operationStarted,
      operationEnded,
      isApplied,
      currentScrollX,
      currentScrollY,
      startGrabbing,
      stopGrabbing,
      grabbing,
    };
  },
});
