import { ModuleSlug } from '@/module/domain/ModuleSlug';

export interface RestAppliedModule {
  slug: string;
}

export const mapAppliedModules = (modules: RestAppliedModule[] | undefined): ModuleSlug[] =>
  (modules ?? []).map(module => new ModuleSlug(module.slug));
