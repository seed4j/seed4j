package com.seed4j.module.domain.javaproperties;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class JHipsterModuleSpringFactories {

  private final Map<PropertyKey, PropertyValue> factories;

  private JHipsterModuleSpringFactories(JHipsterModuleSpringFactoriesBuilder builder) {
    factories = SeedCollections.immutable(builder.factories);
  }

  public static JHipsterModuleSpringFactoriesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleSpringFactoriesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> factories() {
    return factories;
  }

  public static final class JHipsterModuleSpringFactoriesBuilder {

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
