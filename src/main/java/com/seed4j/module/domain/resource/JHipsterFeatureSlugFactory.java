package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.SeedFeatureSlug;
import java.util.Optional;

@FunctionalInterface
public interface JHipsterFeatureSlugFactory {
  String get();

  default Optional<SeedFeatureSlug> build() {
    return SeedFeatureSlug.of(get());
  }
}
