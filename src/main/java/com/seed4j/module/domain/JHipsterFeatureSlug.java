package com.seed4j.module.domain;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public final class JHipsterFeatureSlug extends JHipsterSlug {

  public JHipsterFeatureSlug(String slug) {
    super(slug);
  }

  public static Optional<JHipsterFeatureSlug> of(String feature) {
    return Optional.ofNullable(feature).filter(StringUtils::isNotBlank).map(JHipsterFeatureSlug::new);
  }
}
