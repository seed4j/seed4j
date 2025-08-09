package com.seed4j.module.domain;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public final class SeedFeatureSlug extends SeedSlug {

  public SeedFeatureSlug(String slug) {
    super(slug);
  }

  public static Optional<SeedFeatureSlug> of(String feature) {
    return Optional.ofNullable(feature).filter(StringUtils::isNotBlank).map(SeedFeatureSlug::new);
  }
}
