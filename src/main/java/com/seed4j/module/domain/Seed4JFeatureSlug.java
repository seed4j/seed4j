package com.seed4j.module.domain;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

public final class Seed4JFeatureSlug extends Seed4JSlug {

  public Seed4JFeatureSlug(String slug) {
    super(slug);
  }

  public static Optional<Seed4JFeatureSlug> of(@Nullable String feature) {
    return Optional.ofNullable(feature).filter(StringUtils::isNotBlank).map(Seed4JFeatureSlug::new);
  }
}
