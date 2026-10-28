package com.seed4j.module.domain.javabuild;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public record DependencySlug(String slug) {
  public DependencySlug {
    Assert.notBlank("slug", slug);
  }

  public static Optional<DependencySlug> of(String dependencySlug) {
    return Optional.ofNullable(dependencySlug).filter(StringUtils::isNotBlank).map(DependencySlug::new);
  }
}
