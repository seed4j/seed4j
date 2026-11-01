import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';

export interface LandscapeSelectionElement {
  slug: LandscapeElementId;
  selectable: boolean;
}
