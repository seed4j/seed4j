package tech.jhipster.lite.module.infrastructure.secondary;

import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.shared.error.domain.Assert;

public final class SpringFactoriesUtils {

  private static final String EQUAL = "=";

  private SpringFactoriesUtils() {}

  public static String propertyId(PropertyKey key) {
    Assert.notNull("key", key);
    return key.get() + EQUAL;
  }
}
