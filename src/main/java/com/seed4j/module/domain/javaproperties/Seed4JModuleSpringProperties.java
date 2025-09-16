package com.seed4j.module.domain.javaproperties;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class Seed4JModuleSpringProperties {

  private final Map<PropertyKey, PropertyValue> properties;
  private final Map<PropertyKey, Comment> comments;

  private Seed4JModuleSpringProperties(Seed4JModuleSpringPropertiesBuilder builder) {
    properties = Seed4JCollections.immutable(builder.properties);
    comments = Seed4JCollections.immutable(builder.comments);
  }

  public static Seed4JModuleSpringPropertiesBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleSpringPropertiesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> properties() {
    return properties;
  }

  public Map<PropertyKey, Comment> comments() {
    return comments;
  }

  public static final class Seed4JModuleSpringPropertiesBuilder {

    private final Seed4JModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> properties = new TreeMap<>();
    private final Map<PropertyKey, Comment> comments = new HashMap<>();

    private Seed4JModuleSpringPropertiesBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleSpringPropertiesBuilder set(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.properties.put(key, value);

      return this;
    }

    public Seed4JModuleSpringPropertiesBuilder comment(PropertyKey key, Comment value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.comments.put(key, value);

      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleSpringProperties build() {
      return new Seed4JModuleSpringProperties(this);
    }
  }
}
