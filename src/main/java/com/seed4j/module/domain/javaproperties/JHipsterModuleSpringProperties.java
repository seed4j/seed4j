package com.seed4j.module.domain.javaproperties;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class JHipsterModuleSpringProperties {

  private final Map<PropertyKey, PropertyValue> properties;
  private final Map<PropertyKey, Comment> comments;

  private JHipsterModuleSpringProperties(JHipsterModuleSpringPropertiesBuilder builder) {
    properties = SeedCollections.immutable(builder.properties);
    comments = SeedCollections.immutable(builder.comments);
  }

  public static JHipsterModuleSpringPropertiesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleSpringPropertiesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> properties() {
    return properties;
  }

  public Map<PropertyKey, Comment> comments() {
    return comments;
  }

  public static final class JHipsterModuleSpringPropertiesBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> properties = new TreeMap<>();
    private final Map<PropertyKey, Comment> comments = new HashMap<>();

    private JHipsterModuleSpringPropertiesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleSpringPropertiesBuilder set(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.properties.put(key, value);

      return this;
    }

    public JHipsterModuleSpringPropertiesBuilder comment(PropertyKey key, Comment value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.comments.put(key, value);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleSpringProperties build() {
      return new JHipsterModuleSpringProperties(this);
    }
  }
}
