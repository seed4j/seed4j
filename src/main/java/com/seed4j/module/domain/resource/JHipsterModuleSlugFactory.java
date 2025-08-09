package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.SeedModuleSlug;

public interface JHipsterModuleSlugFactory {
  String get();

  JHipsterModuleRank rank();

  default SeedModuleSlug build() {
    return new SeedModuleSlug(get());
  }
}
