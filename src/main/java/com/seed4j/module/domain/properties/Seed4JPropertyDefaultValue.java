package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

public record Seed4JPropertyDefaultValue(String defaultValue) {
  public Seed4JPropertyDefaultValue {
    Assert.notBlank("defaultValue", defaultValue);
  }

  public static Optional<Seed4JPropertyDefaultValue> of(@Nullable String defaultValue) {
    return Optional.ofNullable(defaultValue).filter(StringUtils::isNotBlank).map(Seed4JPropertyDefaultValue::new);
  }

  public String get() {
    return defaultValue();
  }
}
