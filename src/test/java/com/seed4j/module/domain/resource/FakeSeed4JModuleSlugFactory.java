package com.seed4j.module.domain.resource;

record FakeSeed4JModuleSlugFactory(String slug, Seed4JModuleRank rank) implements Seed4JModuleSlugFactory {
  @Override
  public String get() {
    return slug;
  }

  @Override
  public Seed4JModuleRank rank() {
    return rank;
  }
}
