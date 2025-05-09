import { Optional } from '@/shared/optional/domain/Optional';

import { LandscapeElementId } from './LandscapeElementId';
import { LandscapeSelectionElement } from './LandscapeSelectionElement';

export class LandscapeSelectionTree {
  static readonly EMPTY: LandscapeSelectionTree = new LandscapeSelectionTree([]);

  private readonly selectable: boolean;

  constructor(readonly elements: LandscapeSelectionElement[]) {
    this.selectable = elements.every(element => element.selectable);
  }

  isSelectable(): boolean {
    return this.selectable;
  }

  find(element: LandscapeElementId): Optional<LandscapeSelectionElement> {
    return Optional.ofNullable(this.elements.find(selectionElement => selectionElement.slug.get() === element.get()));
  }
}
