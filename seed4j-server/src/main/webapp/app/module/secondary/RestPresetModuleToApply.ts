import { ModuleSlug } from '@/module/domain/ModuleSlug';

export interface RestPresetModuleToApply {
  slug: string;
}

export const mapPresetModulesToApply = (modules: RestPresetModuleToApply[]): ModuleSlug[] =>
  modules.map(module => new ModuleSlug(module.slug));
