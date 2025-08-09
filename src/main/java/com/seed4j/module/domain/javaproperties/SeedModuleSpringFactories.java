package com.seed4j.module.domain.javaproperties;

import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class SeedModuleSpringFactories {

  private final Map<PropertyKey, PropertyValue> factories;

  private SeedModuleSpringFactories(SeedModuleSpringFactoriesBuilder builder) {
    factories = SeedCollections.immutable(builder.factories);
  }

  public static SeedModuleSpringFactoriesBuilder builder(SeedModuleBuilder module) {
    return new SeedModuleSpringFactoriesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> factories() {
    return factories;
  }

  public static final class SeedModuleSpringFactoriesBuilder {

    private final SeedModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> factories = new HashMap<>();

    private SeedModuleSpringFactoriesBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModuleSpringFactoriesBuilder append(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      factories.merge(key, value, PropertyValue::merge);

      return this;
    }

    public SeedModuleBuilder and() {
      return module;
    }

    public SeedModuleSpringFactories build() {
      return new SeedModuleSpringFactories(this);
    }
  }
}
