package com.seed4j.module.domain.javaproperties;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class Seed4JModuleSpringFactories {

  private final Map<PropertyKey, PropertyValue> factories;

  private Seed4JModuleSpringFactories(Seed4JModuleSpringFactoriesBuilder builder) {
    factories = Seed4JCollections.immutable(builder.factories);
  }

  public static Seed4JModuleSpringFactoriesBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleSpringFactoriesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> factories() {
    return factories;
  }

  public static final class Seed4JModuleSpringFactoriesBuilder {

    private final Seed4JModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> factories = new HashMap<>();

    private Seed4JModuleSpringFactoriesBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleSpringFactoriesBuilder append(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      factories.merge(key, value, PropertyValue::merge);

      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleSpringFactories build() {
      return new Seed4JModuleSpringFactories(this);
    }
  }
}
