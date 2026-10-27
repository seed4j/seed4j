import { LandscapeLevel } from '@/module/domain/landscape/LandscapeLevel';
import { RestLandscapeElement, toLandscapeElement } from '@/module/secondary/RestLandscapeElement';

export interface RestLandscapeLevel {
  elements: RestLandscapeElement[];
}

export const toLandscapeLevel = (level: RestLandscapeLevel): LandscapeLevel => ({
  elements: level.elements.map(toLandscapeElement),
});
