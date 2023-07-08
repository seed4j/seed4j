import { Modules } from '@/module/domain/Modules';
import { ComponentModuleCategory } from './ComponentModulePatchCategory';

export class ComponentModules {
  private constructor(
    public readonly categories: ComponentModuleCategory[],
    public readonly modulesCount: number,
  ) {}

  static fromModules(modules: Modules): ComponentModules {
    return new ComponentModules(
      modules.categories.map(category => ComponentModuleCategory.from(category)),
      modules.modulesCount(),
    );
  }

  public filtered(search: string): ComponentModules {
    const filteredCategories = this.filterCategories(search);

    return new ComponentModules(filteredCategories, this.countModules(filteredCategories));
  }

  private filterCategories(search: string): ComponentModuleCategory[] {
    return this.categories.map(category => this.toFilteredCategory(category, search)).filter(category => category.modules.length !== 0);
  }

  private toFilteredCategory(category: ComponentModuleCategory, search: string): ComponentModuleCategory {
    return {
      name: category.name,
      modules: category.modules.filter(module => {
        return search.split(' ').every(term => this.contains(module.normalizedContent, term));
      }),
    };
  }

  private contains(value: string, search: string): boolean {
    return value.indexOf(search) !== -1;
  }

  private countModules(filteredCategories: ComponentModuleCategory[]): number {
    return filteredCategories.map(category => category.modules.length).reduce((previous, current) => previous + current, 0);
  }
}
