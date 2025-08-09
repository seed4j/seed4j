package com.seed4j.module.domain.resource;

record FakeSeedModuleSlugFactory(String slug, SeedModuleRank rank) implements SeedModuleSlugFactory {
  @Override
  public String get() {
    return slug;
  }

  @Override
  public SeedModuleRank rank() {
    return rank;
  }
}
