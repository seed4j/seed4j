package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.Seed4JFeatureSlug;
import java.util.Optional;

@FunctionalInterface
public interface Seed4JFeatureSlugFactory {
  String get();

  default Optional<Seed4JFeatureSlug> build() {
    return Seed4JFeatureSlug.of(get());
  }
}
