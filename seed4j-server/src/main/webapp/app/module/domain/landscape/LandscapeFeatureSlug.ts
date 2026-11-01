export class LandscapeFeatureSlug {
  constructor(private readonly slug: string) {}

  get(): string {
    return this.slug;
  }
}
