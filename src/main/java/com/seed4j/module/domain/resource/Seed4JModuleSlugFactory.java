package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.Seed4JModuleSlug;

public interface Seed4JModuleSlugFactory {
  String get();

  Seed4JModuleRank rank();

  default Seed4JModuleSlug build() {
    return new Seed4JModuleSlug(get());
  }
}
