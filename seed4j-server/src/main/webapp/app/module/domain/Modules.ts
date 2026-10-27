import { Category } from '@/module/domain/Category';

export class Modules {
  constructor(readonly categories: Category[]) {}

  modulesCount(): number {
    return this.categories.reduce((sum, category) => sum + category.modules.length, 0);
  }
}
