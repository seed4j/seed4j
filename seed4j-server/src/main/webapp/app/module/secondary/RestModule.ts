import { Module } from '@/module/domain/Module';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { RestModulePropertiesDefinitions, toPropertiesDefinitions } from '@/module/secondary/RestModulePropertiesDefinitions';

export interface RestModule {
  slug: string;
  description: string;
  properties?: RestModulePropertiesDefinitions;
  tags?: string[];
}

export const toModule = (restModule: RestModule): Module => ({
  slug: new ModuleSlug(restModule.slug),
  description: restModule.description,
  properties: toPropertiesDefinitions(restModule.properties),
  tags: toTags(restModule.tags),
});

const toTags = (tags: string[] | undefined): string[] => tags ?? [];
