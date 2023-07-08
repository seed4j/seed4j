import { Module } from '@/module/domain/Module';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';

export class ComponentModule {
  private constructor(
    public readonly slug: string,
    public readonly description: string,
    public readonly properties: ModulePropertyDefinition[],
    public readonly tags: string[],
    public readonly normalizedContent: string,
  ) {}

  static from(module: Module): ComponentModule {
    return new ComponentModule(module.slug.get(), module.description, module.properties, module.tags, this.buildNormalizedContent(module));
  }

  private static buildNormalizedContent(module: Module): string {
    return module.description.toLowerCase() + ' ' + module.slug.normalized() + ' ' + module.tags.join(' ');
  }
}
