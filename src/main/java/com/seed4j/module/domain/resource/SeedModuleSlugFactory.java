package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.SeedModuleSlug;

public interface SeedModuleSlugFactory {
  String get();

  SeedModuleRank rank();

  default SeedModuleSlug build() {
    return new SeedModuleSlug(get());
  }
}
