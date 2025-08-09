package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public record SeedPropertyDefaultValue(String defaultValue) {
  public SeedPropertyDefaultValue {
    Assert.notBlank("defaultValue", defaultValue);
  }

  public static Optional<SeedPropertyDefaultValue> of(String defaultValue) {
    return Optional.ofNullable(defaultValue).filter(StringUtils::isNotBlank).map(SeedPropertyDefaultValue::new);
  }

  public String get() {
    return defaultValue();
  }
}
