package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.JHipsterModuleSlug;

public interface JHipsterModuleSlugFactory {
  String get();

  JHipsterModuleRank rank();

  default JHipsterModuleSlug build() {
    return new JHipsterModuleSlug(get());
  }
}
