import { LandscapeElementType } from '@/module/domain/landscape/LandscapeElementType';
import { LandscapeFeature } from '@/module/domain/landscape/LandscapeFeature';
import { LandscapeFeatureSlug } from '@/module/domain/landscape/LandscapeFeatureSlug';
import { RestLandscapeModule, toLandscapeModule } from '@/module/secondary/RestLandscapeModule';

export interface RestLandscapeFeature {
  type: LandscapeElementType;
  slug: string;
  modules: RestLandscapeModule[];
}

export const toLandscapeFeature = (feature: RestLandscapeFeature): LandscapeFeature =>
  new LandscapeFeature(new LandscapeFeatureSlug(feature.slug), feature.modules.map(toLandscapeModule));
