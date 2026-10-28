package com.seed4j.module.domain.javaproperties;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;
import java.util.Collections;
import java.util.LinkedHashMap;

public final class Seed4JModuleApplicationProperties {

  private final Map<PropertyKey, PropertyValue> properties;
  private final Map<PropertyKey, Comment> comments;

  private Seed4JModuleApplicationProperties(Seed4JModuleApplicationPropertiesBuilder builder) {
    properties = Collections.unmodifiableMap(new LinkedHashMap<>(builder.properties));
    comments = Collections.unmodifiableMap(new LinkedHashMap<>(builder.comments));
  }

  public static Seed4JModuleApplicationPropertiesBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleApplicationPropertiesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> properties() {
    return properties;
  }

  public Map<PropertyKey, Comment> comments() {
    return comments;
  }

  public static final class Seed4JModuleApplicationPropertiesBuilder {

    private final Seed4JModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> properties = new TreeMap<>();
    private final Map<PropertyKey, Comment> comments = new HashMap<>();

    private Seed4JModuleApplicationPropertiesBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleApplicationPropertiesBuilder set(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.properties.put(key, value);

      return this;
    }

    public Seed4JModuleApplicationPropertiesBuilder comment(PropertyKey key, Comment value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.comments.put(key, value);

      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleApplicationProperties build() {
      return new Seed4JModuleApplicationProperties(this);
    }
  }
}
