import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';

export interface LandscapeElement {
  slug(): LandscapeElementId;
  slugString(): string;
  allModules(): LandscapeModule[];
  isVisible(): boolean;
  withAllVisibility(visible: boolean): LandscapeElement;
}
