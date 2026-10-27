import { LandscapeElement } from '@/module/domain/landscape/LandscapeElement';
import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';
import { LandscapeSelectionTree } from '@/module/domain/landscape/LandscapeSelectionTree';
import { LandscapeUnselectionTree } from '@/module/domain/landscape/LandscapeUnselectionTree';
import { ModuleOperation } from '@/module/domain/landscape/ModuleOperation';
import { ModuleRank } from '@/module/domain/landscape/ModuleRank';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModuleSlug } from '@/module/domain/ModuleSlug';

export interface LandscapeModuleInformation {
  slug: ModuleSlug;
  operation: ModuleOperation;
  properties: ModulePropertyDefinition[];
  dependencies: LandscapeElementId[];
  rank: ModuleRank;
}

export interface LandscapeModuleContext {
  selected: boolean;
  applied: boolean;
  selectionTree: LandscapeSelectionTree;
  unselectionTree: LandscapeUnselectionTree;
  visible: boolean;
}

const INITIAL_CONTEXT: LandscapeModuleContext = {
  selected: false,
  applied: false,
  selectionTree: LandscapeSelectionTree.EMPTY,
  unselectionTree: LandscapeUnselectionTree.EMPTY,
  visible: true,
};

export class LandscapeModule implements LandscapeElement {
  private constructor(
    private readonly information: LandscapeModuleInformation,
    private readonly context: LandscapeModuleContext,
  ) {}

  static initialState(information: LandscapeModuleInformation): LandscapeModule {
    return new LandscapeModule(information, INITIAL_CONTEXT);
  }

  slugString(): string {
    return this.slug().get();
  }

  slug(): ModuleSlug {
    return this.information.slug;
  }

  dependencies(): LandscapeElementId[] {
    return this.information.dependencies;
  }

  rank(): ModuleRank {
    return this.information.rank;
  }

  operation(): string {
    return this.information.operation;
  }

  properties(): ModulePropertyDefinition[] {
    return this.information.properties;
  }

  allModules(): LandscapeModule[] {
    return [this];
  }

  inContext(context: LandscapeModuleContext): LandscapeModule {
    return new LandscapeModule(this.information, context);
  }

  isSelected(): boolean {
    return this.context.selected;
  }

  isApplied(): boolean {
    return this.context.applied;
  }

  selectionTree(): LandscapeSelectionTree {
    return this.context.selectionTree;
  }

  unselectionTree(): LandscapeUnselectionTree {
    return this.context.unselectionTree;
  }

  isVisible(): boolean {
    return this.context.visible;
  }

  withAllVisibility(visible: boolean): LandscapeElement {
    return new LandscapeModule(this.information, { ...this.context, visible });
  }
}
