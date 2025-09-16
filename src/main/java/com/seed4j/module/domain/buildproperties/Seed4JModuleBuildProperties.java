package com.seed4j.module.domain.buildproperties;

import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.SetBuildProperty;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class Seed4JModuleBuildProperties {

  private final Map<PropertyKey, PropertyValue> properties;

  private Seed4JModuleBuildProperties(Seed4JModuleBuildPropertiesBuilder<?> builder) {
    properties = Seed4JCollections.immutable(builder.properties);
  }

  public static <T> Seed4JModuleBuildPropertiesBuilder<T> builder(T parent) {
    return new Seed4JModuleBuildPropertiesBuilder<>(parent);
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

  public static final class Seed4JModuleBuildPropertiesBuilder<T> {

    private final T parent;
    private final Map<PropertyKey, PropertyValue> properties = new HashMap<>();

    private Seed4JModuleBuildPropertiesBuilder(T parent) {
      Assert.notNull("parent", parent);

      this.parent = parent;
    }

    public Seed4JModuleBuildPropertiesBuilder<T> set(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      this.properties.put(key, value);

      return this;
    }

    public T and() {
      return parent;
    }

    public Seed4JModuleBuildProperties build() {
      return new Seed4JModuleBuildProperties(this);
    }
  }
}
