package tech.jhipster.lite.module.domain.javabuild;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.shared.error.domain.Assert;

public record DependencySlug(String slug) {
  public DependencySlug {
    Assert.notBlank("slug", slug);
  }

  public static Optional<DependencySlug> of(String dependencySlug) {
    return Optional.ofNullable(dependencySlug).filter(StringUtils::isNotBlank).map(DependencySlug::new);
  }
}
