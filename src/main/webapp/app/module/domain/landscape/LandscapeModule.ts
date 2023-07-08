import { ModulePropertyDefinition } from '../ModulePropertyDefinition';
import { ModuleSlug } from '../ModuleSlug';
import { LandscapeElement } from './LandscapeElement';
import { LandscapeElementId } from './LandscapeElementId';
import { LandscapeSelectionTree } from './LandscapeSelectionTree';
import { LandscapeUnselectionTree } from './LandscapeUnselectionTree';
import { ModuleOperation } from './ModuleOperation';

export interface LandscapeModuleInformation {
  slug: ModuleSlug;
  operation: ModuleOperation;
  properties: ModulePropertyDefinition[];
  dependencies: LandscapeElementId[];
}

export interface LandscapeModuleContext {
  selected: boolean;
  applied: boolean;
  selectionTree: LandscapeSelectionTree;
  unselectionTree: LandscapeUnselectionTree;
}

const INITIAL_CONTEXT: LandscapeModuleContext = {
  selected: false,
  applied: false,
  selectionTree: LandscapeSelectionTree.EMPTY,
  unselectionTree: LandscapeUnselectionTree.EMPTY,
};

export class LandscapeModule implements LandscapeElement {
  private constructor(
    private readonly information: LandscapeModuleInformation,
    private readonly context: LandscapeModuleContext,
  ) {}

  public static initialState(information: LandscapeModuleInformation): LandscapeModule {
    return new LandscapeModule(information, INITIAL_CONTEXT);
  }

  public slugString(): string {
    return this.slug().get();
  }

  public slug(): ModuleSlug {
    return this.information.slug;
  }

  public dependencies(): LandscapeElementId[] {
    return this.information.dependencies;
  }

  public operation(): string {
    return this.information.operation;
  }

  public properties(): ModulePropertyDefinition[] {
    return this.information.properties;
  }

  public allModules(): LandscapeModule[] {
    return [this];
  }

  public inContext(context: LandscapeModuleContext): LandscapeModule {
    return new LandscapeModule(this.information, context);
  }

  public isSelected(): boolean {
    return this.context.selected;
  }

  public isApplied(): boolean {
    return this.context.applied;
  }

  public selectionTree(): LandscapeSelectionTree {
    return this.context.selectionTree;
  }

  public unselectionTree(): LandscapeUnselectionTree {
    return this.context.unselectionTree;
  }
}
