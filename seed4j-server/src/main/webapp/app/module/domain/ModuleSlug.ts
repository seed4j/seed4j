export class ModuleSlug {
  constructor(private readonly slug: string) {}

  get(): string {
    return this.slug;
  }

  normalized(): string {
    return this.slug.toLowerCase();
  }
}
