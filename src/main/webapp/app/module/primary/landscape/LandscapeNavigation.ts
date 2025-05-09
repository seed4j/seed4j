import { LandscapeElement } from '@/module/domain/landscape/LandscapeElement';
import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';
import { LandscapeLevel } from '@/module/domain/landscape/LandscapeLevel';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { ModuleSlug } from '@/module/domain/ModuleSlug';

export class LandscapeNavigation {
  private currentModule: number;
  private currentElement: number;
  private currentLevel: number;
  private readonly landscapeElements: Map<string, HTMLElement>;
  private readonly levels: LandscapeLevel[];

  constructor(landscapeElements: Map<string, HTMLElement>, levels: LandscapeLevel[]) {
    [this.currentLevel, this.currentElement, this.currentModule] = this.setValues(0, 0, 0);
    this.landscapeElements = landscapeElements;
    this.levels = levels;
  }

  private readonly level = (): LandscapeLevel => this.levels[this.currentLevel];

  private readonly getLevelByIndex = (index: number): LandscapeLevel => this.levels[index];

  private readonly element = (): LandscapeElement => this.level().elements[this.currentElement];

  private readonly module = (): LandscapeModule => this.element().allModules()[this.currentModule];

  getSlug = (): ModuleSlug => this.module().slug();

  goUp(): void {
    if (this.currentModule == 0) {
      this.currentElement = this.decrease(this.currentElement);
      this.currentModule = this.element().allModules().length - 1;
    } else {
      this.currentModule = this.decrease(this.currentModule);
    }
  }

  goDown(): void {
    if (this.currentModule == this.element().allModules().length - 1) {
      this.currentElement = this.increase(this.currentElement, this.level().elements.length - 1);
      this.currentModule = 0;
    } else {
      this.currentModule = this.increase(this.currentModule, this.element().allModules().length - 1);
    }
  }

  goLeft(): void {
    const currentModule = this.module().slug();

    this.currentLevel = this.decrease(this.currentLevel);

    const [elementIndex, moduleIndex] = this.calculatePosition(currentModule);

    this.updateCursor(this.currentLevel, elementIndex, moduleIndex);
  }

  goRight(): void {
    const currentModule = this.module().slug();

    this.currentLevel = this.increase(this.currentLevel, this.levels.length - 1);

    const [elementIndex, moduleIndex] = this.calculatePosition(currentModule);

    this.updateCursor(this.currentLevel, elementIndex, moduleIndex);
  }

  goToDependency(): void {
    if (this.module().dependencies().length == 0) {
      return;
    }

    const [levelIndex, elementIndex, moduleIndex] = this.findDependencyPosition();

    this.updateCursor(levelIndex, elementIndex, moduleIndex);
  }

  goToDependent(): void {
    const [levelIndex, elementIndex, moduleIndex] = this.findDependentPosition();
    this.updateCursor(levelIndex, elementIndex, moduleIndex);
  }

  private readonly isInModuleDependencies = (dependencies: LandscapeElementId[]): boolean =>
    dependencies.some(dependency => dependency.get() == this.module().slug().get());

  private readonly findModuleIndexInDependencies = (modules: LandscapeModule[]): number =>
    modules.findIndex(module => this.isInModuleDependencies(module.dependencies()));

  private readonly isInDependencies = (slug: string): boolean =>
    this.module()
      .dependencies()
      .some(dependency => dependency.get() == slug);

  private readonly findModuleIndexWithSlug = (modules: LandscapeModule[]): number =>
    modules.findIndex(module => this.isInDependencies(module.slug().get()));

  private findDependencyElementPosition(elements: LandscapeElement[]): [number, number] {
    let moduleIndex = 0;
    const elementIndex = elements.findIndex(element => {
      if (this.isInDependencies(element.slug().get())) {
        moduleIndex = 0;
        return true;
      }
      moduleIndex = this.findModuleIndexWithSlug(element.allModules());
      return moduleIndex >= 0;
    });

    return [elementIndex, moduleIndex];
  }

  private findDependencyPosition(): [number, number, number] {
    const levelIndex = this.currentLevel - 1;
    const [elementIndex, moduleIndex] = this.findDependencyElementPosition(this.getLevelByIndex(levelIndex).elements);

    return [levelIndex, elementIndex, moduleIndex];
  }

  private findDependentElementPosition(elements: LandscapeElement[]): [number, number] {
    let moduleIndex = 0;
    const elementIndex = elements.findIndex(element => {
      moduleIndex = this.findModuleIndexInDependencies(element.allModules());
      return moduleIndex >= 0;
    });

    return [elementIndex, moduleIndex];
  }

  private findDependentPosition(): [number, number, number] {
    let [levelIndex, elementIndex, moduleIndex] = this.setValues(this.currentLevel, this.currentElement, this.currentModule);

    while (levelIndex < this.levels.length - 1) {
      levelIndex += 1;

      [elementIndex, moduleIndex] = this.findDependentElementPosition(this.getLevelByIndex(levelIndex).elements);
      if (elementIndex >= 0) {
        break;
      }
    }

    if (elementIndex == -1) {
      return [this.currentLevel, this.currentElement, this.currentModule];
    }

    return [levelIndex, elementIndex, moduleIndex];
  }

  private readonly getHtmlElement = (moduleSlug: string): HTMLElement => this.landscapeElements.get(moduleSlug) as HTMLElement;

  private readonly calculateDistance = (moduleHtmlElement: HTMLElement, currentModuleHtml: HTMLElement): number =>
    Math.abs(moduleHtmlElement.getBoundingClientRect().y - currentModuleHtml.getBoundingClientRect().y);

  private calculatePosition(currentModule: ModuleSlug): [number, number] {
    const currentModuleHtml = this.getHtmlElement(currentModule.get());
    let [elementIndex, moduleIndex, smallestDistance] = this.setValues(0, 0, Infinity);

    this.level().elements.forEach((element, element_index) => {
      element.allModules().forEach((module, module_index) => {
        const moduleHtmlElement = this.getHtmlElement(module.slug().get());
        const distance = this.calculateDistance(moduleHtmlElement, currentModuleHtml);
        if (distance < smallestDistance) {
          [smallestDistance, elementIndex, moduleIndex] = this.setValues(distance, element_index, module_index);
        }
      });
    });

    return [elementIndex, moduleIndex];
  }

  private readonly setValues = (i1: number, i2: number, i3: number): [number, number, number] => [i1, i2, i3];

  private readonly increase = (value: number, max: number): number => (value < max ? value + 1 : max);

  private readonly decrease = (value: number): number => (value > 0 ? value - 1 : 0);

  private updateCursor(currentLevel: number, currentElement: number, currentModule: number): void {
    [this.currentLevel, this.currentElement, this.currentModule] = this.setValues(currentLevel, currentElement, currentModule);
  }
}
