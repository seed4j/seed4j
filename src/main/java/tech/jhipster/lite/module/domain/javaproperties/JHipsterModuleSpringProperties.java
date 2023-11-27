package tech.jhipster.lite.module.domain.javaproperties;

import java.util.HashMap;
import java.util.Map;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleSpringProperties {

  private final Map<PropertyKey, PropertyValue> properties;
  private final Map<PropertyKey, Comment> comments;

  private JHipsterModuleSpringProperties(JHipsterModuleSpringPropertiesBuilder builder) {
    properties = JHipsterCollections.immutable(builder.properties);
    comments = JHipsterCollections.immutable(builder.comments);
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

  public static class JHipsterModuleSpringPropertiesBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> properties = new HashMap<>();
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
