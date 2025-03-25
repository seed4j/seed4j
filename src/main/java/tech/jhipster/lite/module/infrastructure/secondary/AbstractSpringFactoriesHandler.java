package tech.jhipster.lite.module.infrastructure.secondary;

import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.shared.error.domain.Assert;

public abstract class AbstractSpringFactoriesHandler {

  public void append(PropertyKey key, PropertyValue value) {
    Assert.notNull("key", key);
    Assert.notNull("value", value);

    updateFactories(key, value);
  }

  protected abstract void updateFactories(PropertyKey key, PropertyValue value);
}
