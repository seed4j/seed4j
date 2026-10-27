import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';

export class LandscapeUnselectionTree {
  static readonly EMPTY: LandscapeUnselectionTree = new LandscapeUnselectionTree([]);

  constructor(readonly elements: LandscapeElementId[]) {}

  includes(element: LandscapeElementId): boolean {
    return this.elements.some(unselectedElement => unselectedElement.get() === element.get());
  }
}
