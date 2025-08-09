package com.seed4j.module.domain.buildproperties;

import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.SetBuildProperty;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class SeedModuleBuildProperties {

  private final Map<PropertyKey, PropertyValue> properties;

  private SeedModuleBuildProperties(JHipsterModuleBuildPropertiesBuilder<?> builder) {
    properties = SeedCollections.immutable(builder.properties);
  }

  public static <T> JHipsterModuleBuildPropertiesBuilder<T> builder(T parent) {
    return new JHipsterModuleBuildPropertiesBuilder<>(parent);
  }

  public JavaBuildCommands buildChanges() {
    return new JavaBuildCommands(
      properties
        .entrySet()
        .stream()
        .map(property -> new SetBuildProperty(new BuildProperty(property.getKey(), property.getValue())))
        .toList()
    );
  }

  public Stream<JavaBuildCommand> buildChanges(BuildProfileId buildProfile) {
    return properties
      .entrySet()
      .stream()
      .map(property -> new SetBuildProperty(new BuildProperty(property.getKey(), property.getValue()), buildProfile));
  }

  public static final class JHipsterModuleBuildPropertiesBuilder<T> {

    private final T parent;
    private final Map<PropertyKey, PropertyValue> properties = new HashMap<>();

    private JHipsterModuleBuildPropertiesBuilder(T parent) {
      Assert.notNull("parent", parent);

      this.parent = parent;
    }

    public JHipsterModuleBuildPropertiesBuilder<T> set(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.properties.put(key, value);

      return this;
    }

    public T and() {
      return parent;
    }

    public SeedModuleBuildProperties build() {
      return new SeedModuleBuildProperties(this);
    }
  }
}
