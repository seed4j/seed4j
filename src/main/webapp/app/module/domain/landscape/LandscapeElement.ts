import { LandscapeElementId } from './LandscapeElementId';
import { LandscapeModule } from './LandscapeModule';

export interface LandscapeElement {
  slug(): LandscapeElementId;
  slugString(): string;
  allModules(): LandscapeModule[];
  isVisible(): boolean;
}
