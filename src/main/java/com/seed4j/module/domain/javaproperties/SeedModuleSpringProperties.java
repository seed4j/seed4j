package com.seed4j.module.domain.javaproperties;

import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class SeedModuleSpringProperties {

  private final Map<PropertyKey, PropertyValue> properties;
  private final Map<PropertyKey, Comment> comments;

  private SeedModuleSpringProperties(SeedModuleSpringPropertiesBuilder builder) {
    properties = SeedCollections.immutable(builder.properties);
    comments = SeedCollections.immutable(builder.comments);
  }

  public static SeedModuleSpringPropertiesBuilder builder(SeedModuleBuilder module) {
    return new SeedModuleSpringPropertiesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> properties() {
    return properties;
  }

  public Map<PropertyKey, Comment> comments() {
    return comments;
  }

  public static final class SeedModuleSpringPropertiesBuilder {

    private final SeedModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> properties = new TreeMap<>();
    private final Map<PropertyKey, Comment> comments = new HashMap<>();

    private SeedModuleSpringPropertiesBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModuleSpringPropertiesBuilder set(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.properties.put(key, value);

      return this;
    }

    public SeedModuleSpringPropertiesBuilder comment(PropertyKey key, Comment value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.comments.put(key, value);

      return this;
    }

    public SeedModuleBuilder and() {
      return module;
    }

    public SeedModuleSpringProperties build() {
      return new SeedModuleSpringProperties(this);
    }
  }
}
