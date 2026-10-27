import { LandscapeElement } from '@/module/domain/landscape/LandscapeElement';
import { RestLandscapeFeature, toLandscapeFeature } from '@/module/secondary/RestLandscapeFeature';
import { RestLandscapeModule, toLandscapeModule } from '@/module/secondary/RestLandscapeModule';

export type RestLandscapeElement = RestLandscapeModule | RestLandscapeFeature;

export const toLandscapeElement = (element: RestLandscapeElement): LandscapeElement => {
  switch (element.type) {
    case 'FEATURE':
      return toLandscapeFeature(element as RestLandscapeFeature);
    case 'MODULE':
      return toLandscapeModule(element as RestLandscapeModule);
  }
};
