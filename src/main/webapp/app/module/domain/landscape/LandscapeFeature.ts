import { LandscapeElement } from './LandscapeElement';
import { LandscapeFeatureSlug } from './LandscapeFeatureSlug';
import { LandscapeModule } from './LandscapeModule';

export class LandscapeFeature implements LandscapeElement {
  private readonly visible: boolean;

  constructor(
    private readonly featureSlug: LandscapeFeatureSlug,
    readonly modules: LandscapeModule[],
    visible: boolean = true,
  ) {
    this.visible = visible;
  }

  slugString(): string {
    return this.slug().get();
  }

  slug(): LandscapeFeatureSlug {
    return this.featureSlug;
  }

  allModules(): LandscapeModule[] {
    return this.modules;
  }

  isVisible(): boolean {
    return this.visible;
  }
}
