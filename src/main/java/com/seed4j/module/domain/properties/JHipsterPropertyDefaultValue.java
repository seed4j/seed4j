package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public record JHipsterPropertyDefaultValue(String defaultValue) {
  public JHipsterPropertyDefaultValue {
    Assert.notBlank("defaultValue", defaultValue);
  }

  public static Optional<JHipsterPropertyDefaultValue> of(String defaultValue) {
    return Optional.ofNullable(defaultValue).filter(StringUtils::isNotBlank).map(JHipsterPropertyDefaultValue::new);
  }

  public String get() {
    return defaultValue();
  }
}
