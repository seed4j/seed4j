package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.JHipsterFeatureSlug;
import java.util.Optional;

@FunctionalInterface
public interface JHipsterFeatureSlugFactory {
  String get();

  default Optional<JHipsterFeatureSlug> build() {
    return JHipsterFeatureSlug.of(get());
  }
}
