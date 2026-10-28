import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';
import { LandscapeElementType } from '@/module/domain/landscape/LandscapeElementType';
import { LandscapeFeatureSlug } from '@/module/domain/landscape/LandscapeFeatureSlug';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { ModuleRank } from '@/module/domain/landscape/ModuleRank';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { RestModulePropertiesDefinitions, toPropertiesDefinitions } from '@/module/secondary/RestModulePropertiesDefinitions';

export interface RestLandscapeModule {
  type: LandscapeElementType;
  slug: string;
  operation: string;
  properties: RestModulePropertiesDefinitions;
  dependencies?: RestLandscapeDependency[];
  rank: ModuleRank;
}

export interface RestLandscapeDependency {
  slug: string;
  type: LandscapeElementType;
}

export const toLandscapeModule = (module: RestLandscapeModule): LandscapeModule =>
  LandscapeModule.initialState({
    slug: new ModuleSlug(module.slug),
    operation: module.operation,
    properties: toPropertiesDefinitions(module.properties),
    dependencies: toModuleDependencies(module.dependencies),
    rank: module.rank,
  });

const toModuleDependencies = (dependencies: RestLandscapeDependency[] | undefined): LandscapeElementId[] => {
  if (dependencies === undefined) {
    return [];
  }

  return dependencies.map(dependency => {
    switch (dependency.type) {
      case 'FEATURE':
        return new LandscapeFeatureSlug(dependency.slug);
      case 'MODULE':
        return new ModuleSlug(dependency.slug);
    }
  });
};
