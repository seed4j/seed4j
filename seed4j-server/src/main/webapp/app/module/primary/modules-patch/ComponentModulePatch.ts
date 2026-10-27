import { Module } from '@/module/domain/Module';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';

export class ComponentModule {
  private constructor(
    readonly slug: string,
    readonly description: string,
    readonly properties: ModulePropertyDefinition[],
    readonly tags: string[],
    readonly normalizedContent: string,
  ) {}

  static from(module: Module): ComponentModule {
    return new ComponentModule(module.slug.get(), module.description, module.properties, module.tags, this.buildNormalizedContent(module));
  }

  private static readonly buildNormalizedContent = (module: Module): string =>
    `${module.description.toLowerCase()} ${module.slug.normalized()} ${module.tags.join(' ')}`;
}
