import { ApplicationListener } from '@/common/primary/applicationlistener/ApplicationListener';
import { Loader } from '@/loader/primary/Loader';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { defineComponent, inject, nextTick, onBeforeUnmount, onMounted, Ref, ref } from 'vue';
import { LandscapeModuleVue } from '../landscape-module';
import { LandscapeLoaderVue } from '../landscape-loader';
import { LandscapeMiniMapVue } from '../landscape-minimap';
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
import { ModuleParametersRepository } from '@/module/domain/ModuleParametersRepository';
import { LandscapeNavigation } from './LandscapeNavigation';
import { AnchorPointState } from '@/module/domain/AnchorPointState';

export default defineComponent({
  name: 'LandscapeVue',
  components: { LandscapeModuleVue, ModulePropertiesFormVue, ProjectActionsVue, IconVue, LandscapeLoaderVue, LandscapeMiniMapVue },
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

    const canLoadMiniMap = ref(false);

    const landscapeContainer = ref<HTMLElement>(document.createElement('div'));
    const landscapeConnectors = ref<LandscapeConnector[]>([]);
    const landscapeSize = ref<LandscapeConnectorsSize>(emptyLandscapeSize());
    const landscapeElements = ref(new Map<string, HTMLElement>());
    const landscapeNavigation = ref(Loader.loading<LandscapeNavigation>());

    const emphasizedModule = ref<ModuleSlug>();

    const moduleParameters = inject('moduleParameters') as ModuleParametersRepository;
    const folderPath = ref(moduleParameters.getCurrentFolderPath());
    const moduleParametersValues = ref(moduleParameters.get(folderPath.value));
    const anchorPointModulesMap = ref(new Map<string, AnchorPointState>());

    let commitModule = true;

    const isMoving: Ref<boolean> = ref(false);
    const startX: Ref<number> = ref(0);
    const startY: Ref<number> = ref(0);
    const currentScrollX: Ref<number> = ref(0);
    const currentScrollY: Ref<number> = ref(0);

    const operationInProgress = ref(false);

    onMounted(() => {
      modules
        .landscape()
        .then(response => loadLandscape(response))
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

    const startGrabbing = (mouseEvent: MouseEvent): void => {
      if (mouseEvent.preventDefault) {
        mouseEvent.preventDefault();
      }
      isMoving.value = true;
      startX.value = mouseEvent.clientX;
      startY.value = mouseEvent.clientY;
      const rect = landscapeContainer.value;
      currentScrollX.value = rect.scrollLeft;
      currentScrollY.value = rect.scrollTop;
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
      landscapeScroller.scroll(landscapeContainer.value, scrollX, scrollY);
    };

    const landscapeNavigationValue = (): LandscapeNavigation => {
      return landscapeNavigation.value.value();
    };

    const loadLandscape = async (response: Landscape): Promise<void> => {
      landscape.value.loaded(response);
      levels.value.loaded(response.standaloneLevels());

      await nextTick().then(updateConnectors);
      landscapeNavigation.value.loaded(new LandscapeNavigation(landscapeElements.value, levels.value.value()));
      document.addEventListener('keydown', handleKeyboard);
      applicationListener.addEventListener('resize', updateConnectors);

      canLoadMiniMap.value = true;
      loadAnchorPointModulesMap();
    };

    const loadAnchorPointModulesMap = (): void => {
      landscapeConnectors.value.forEach(e => {
        const startingElementSlug = e.startingElement.get();
        const endingElementSlug = e.endingElement.get();

        const startingElementSlugExists = anchorPointModulesMap.value.get(startingElementSlug);
        if (!startingElementSlugExists) {
          anchorPointModulesMap.value.set(startingElementSlug, { atStart: true, atEnd: false });
        } else {
          anchorPointModulesMap.value.set(startingElementSlug, { atStart: true, atEnd: startingElementSlugExists.atEnd });
        }

        const endingElementSlugExists = anchorPointModulesMap.value.get(endingElementSlug);
        if (!endingElementSlugExists) {
          anchorPointModulesMap.value.set(endingElementSlug, { atStart: false, atEnd: true });
        } else {
          anchorPointModulesMap.value.set(endingElementSlug, { atStart: endingElementSlugExists.atStart, atEnd: true });
        }
      });
    };

    type Navigation = 'ArrowLeft' | 'ArrowRight' | 'ArrowUp' | 'ArrowDown' | 'Space';
    type NavigationAction = {
      [key in Navigation]: keyof LandscapeNavigation;
    };

    const isNavigation = (code: string): code is Navigation => {
      return ['ArrowLeft', 'ArrowRight', 'ArrowUp', 'ArrowDown', 'Space'].includes(code);
    };

    const prepareNavigationActions = (ctrl: boolean): NavigationAction => {
      return {
        ArrowUp: 'goUp',
        ArrowDown: 'goDown',
        ArrowLeft: ctrl ? 'goToDependency' : 'goLeft',
        ArrowRight: ctrl ? 'goToDependent' : 'goRight',
        Space: 'getSlug',
      };
    };

    const handleNavigation = (code: Navigation, ctrl: boolean): void => {
      const navigationActions = prepareNavigationActions(ctrl);

      const module = landscapeNavigationValue()[navigationActions[code]]();
      if (module) {
        toggleModule(module);
      }
    };

    const handleKeyboard = (event: Event): void => {
      // Disable arrows action in input field
      if (isInputActiveElement()) {
        return;
      }

      const keyboardEvent = event as KeyboardEvent;

      if (isNavigation(keyboardEvent.code)) {
        handleNavigation(keyboardEvent.code, keyboardEvent.ctrlKey);
        emphasizeModule(landscapeNavigationValue().getSlug());
      }
    };

    onBeforeUnmount(() => {
      applicationListener.removeEventListener('resize', updateConnectors);
      document.removeEventListener('keydown', handleKeyboard);
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
        }),
      );
    };

    const buildConnectorsSize = (): LandscapeConnectorsSize => ({
      width: Math.max.apply(
        null,
        landscapeConnectors.value.flatMap(connector => connector.positions).map(position => position.x),
      ),
      height: Math.max.apply(
        null,
        landscapeConnectors.value.flatMap(connector => connector.positions).map(position => position.y),
      ),
    });

    const isFeature = (element: LandscapeElement): boolean => {
      return element instanceof LandscapeFeature;
    };

    const landscapeClass = (): string => {
      const hasEmphasizedModule = emphasizedModule.value !== undefined;
      return `jhipster-landscape-map jhlite-menu-content-template--content${hasEmphasizedModule ? ' has-emphasized-module' : ''}`;
    };

    const modeSwitchClass = (mode: DisplayMode): string => {
      if (selectedMode.value === mode) {
        return '-selected';
      }

      return '-not-selected';
    };

    const selectMode = async (mode: DisplayMode): Promise<void> => {
      selectedMode.value = mode;

      await nextTick().then(updateConnectors);
    };

    const anchorPointClass = (module: LandscapeElementId): string => {
      if (module instanceof LandscapeFeatureSlug) {
        return '';
      }

      let className = '';

      const anchorPointState = anchorPointModulesMap.value.get(module.get());
      if (anchorPointState) {
        if (anchorPointState.atStart) {
          className += ' -left-anchor-point';
        }

        if (anchorPointState.atEnd) {
          className += ' -right-anchor-point';
        }
      }

      return className;
    };

    const elementFlavor = (module: LandscapeElementId): string => {
      return (
        operationInProgressClass() +
        selectionHighlightClass(module) +
        unselectionHighlightClass(module) +
        selectionClass(module) +
        applicationClass(module) +
        flavorClass() +
        anchorPointClass(module)
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
      // Remove the focus on input field
      if (isInputActiveElement()) {
        (document?.activeElement as HTMLElement).blur();
      }
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
        property => property.mandatory && empty(moduleParametersValues.value.get(property.key)) && empty(property.defaultValue),
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
      moduleParametersValues.value = moduleParameters.get(folderPath.value);
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
          moduleParametersValues.value.set(property.key, property.value);
        }
      });
      moduleParameters.store(folderPath.value, moduleParametersValues.value);
    };

    const unknownProperty = (key: string) => {
      return !moduleParametersValues.value.has(key);
    };

    const updateProperty = (property: ModuleParameter): void => {
      moduleParametersValues.value.set(property.key, property.value);
      moduleParameters.store(folderPath.value, moduleParametersValues.value);
    };

    const deleteProperty = (key: string): void => {
      moduleParametersValues.value.delete(key);
      moduleParameters.store(folderPath.value, moduleParametersValues.value);
    };

    const applyNewModules = (): void => {
      applyModules(landscapeValue().notAppliedSelectedModules(), true);
    };

    const applyAllModules = (): void => {
      applyModules(landscapeValue().selectedModules(), true);
    };

    const applyModule = (module: ModuleSlug): void => {
      applyModules([module], false);
    };

    const applyModules = (modulesToApply: ModuleSlug[], selectModules: boolean): void => {
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
          parameters: moduleParametersValues.value,
        })
        .then(() => {
          operationEnded();
          if (selectModules) {
            landscape.value.loaded(landscapeValue().appliedModules(modulesToApply));
          }

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

    const isInputActiveElement = (): boolean => {
      return document?.activeElement?.tagName === 'INPUT';
    };

    return {
      levels,
      isFeature,
      landscapeConnectors,
      landscapeSize,
      landscapeElements,
      landscapeContainer,
      landscapeClass,
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
      moduleParametersValues,
      updateModuleCommit,
      updateFolderPath,
      projectFolderUpdated,
      updateProperty,
      deleteProperty,
      applyModule,
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
      canLoadMiniMap,
    };
  },
});
