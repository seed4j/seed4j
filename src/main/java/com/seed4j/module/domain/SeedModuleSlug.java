package com.seed4j.module.domain;

public final class SeedModuleSlug extends SeedSlug {

  public SeedModuleSlug(String slug) {
    super(slug);
  }

  @Override
  public String toString() {
    return get();
  }
}
