package tech.jhipster.lite.module.domain.properties;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.shared.error.domain.Assert;

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
