package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public record SeedPropertyDescription(String description) {
  public SeedPropertyDescription {
    Assert.field("description", description).notBlank().maxLength(100);
  }

  public static Optional<SeedPropertyDescription> of(String description) {
    return Optional.ofNullable(description).filter(StringUtils::isNotBlank).map(SeedPropertyDescription::new);
  }

  public String get() {
    return description();
  }
}
