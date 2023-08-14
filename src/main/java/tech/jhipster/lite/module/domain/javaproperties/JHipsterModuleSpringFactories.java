package tech.jhipster.lite.module.domain.javaproperties;

import java.util.*;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleSpringFactories {

  private final Map<PropertyKey, PropertyValue> factories;

  private JHipsterModuleSpringFactories(JHipsterModuleSpringFactoriesBuilder builder) {
    factories = JHipsterCollections.immutable(builder.factories);
  }

  public static JHipsterModuleSpringFactoriesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleSpringFactoriesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> factories() {
    return factories;
  }

  public static class JHipsterModuleSpringFactoriesBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> factories = new HashMap<>();

    private JHipsterModuleSpringFactoriesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleSpringFactoriesBuilder append(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      factories.merge(key, value, PropertyValue::merge);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleSpringFactories build() {
      return new JHipsterModuleSpringFactories(this);
    }
  }
}
