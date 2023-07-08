import { LandscapeModule } from './LandscapeModule';
import { LandscapeFeatureSlug } from './LandscapeFeatureSlug';
import { LandscapeElement } from './LandscapeElement';

export class LandscapeFeature implements LandscapeElement {
  constructor(
    private readonly featureSlug: LandscapeFeatureSlug,
    public readonly modules: LandscapeModule[],
  ) {}

  public slugString(): string {
    return this.slug().get();
  }

  public slug(): LandscapeFeatureSlug {
    return this.featureSlug;
  }

  public allModules(): LandscapeModule[] {
    return this.modules;
  }
}
