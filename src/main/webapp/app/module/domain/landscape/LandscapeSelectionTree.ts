import { Optional } from '@/shared/optional/domain/Optional';
import { LandscapeElementId } from './LandscapeElementId';
import { LandscapeSelectionElement } from './LandscapeSelectionElement';

export class LandscapeSelectionTree {
  public static readonly EMPTY: LandscapeSelectionTree = new LandscapeSelectionTree([]);

  private readonly selectable: boolean;

  public constructor(public readonly elements: LandscapeSelectionElement[]) {
    this.selectable = elements.every(element => element.selectable);
  }

  public isSelectable(): boolean {
    return this.selectable;
  }

  public find(element: LandscapeElementId): Optional<LandscapeSelectionElement> {
    return Optional.ofUndefinable(this.elements.find(selectionElement => selectionElement.slug.get() === element.get()));
  }
}
