package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

public record Seed4JPropertyDescription(String description) {
  public Seed4JPropertyDescription {
    Assert.field("description", description).notBlank().maxLength(100);
  }

  public static Optional<Seed4JPropertyDescription> of(@Nullable String description) {
    return Optional.ofNullable(description).filter(StringUtils::isNotBlank).map(Seed4JPropertyDescription::new);
  }

  public String get() {
    return description();
  }
}
