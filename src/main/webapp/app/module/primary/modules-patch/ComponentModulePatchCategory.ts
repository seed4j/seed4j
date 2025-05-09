import { Category } from '@/module/domain/Category';

import { ComponentModule } from './ComponentModulePatch';

export class ComponentModuleCategory {
  private constructor(
    readonly name: string,
    readonly modules: ComponentModule[],
  ) {}

  static from(category: Category): ComponentModuleCategory {
    return new ComponentModuleCategory(
      category.name,
      category.modules.map(module => ComponentModule.from(module)),
    );
  }
}
