package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class SpringProperty implements SpringPropertyTypeFileName {

  private final SpringPropertyType type;
  private final PropertyKey key;
  private final PropertyValue value;
  private final SpringProfile profile;

  private SpringProperty(SpringPropertyBuilder builder) {
    Assert.notNull("key", builder.key);
    Assert.notNull("value", builder.value);

    type = builder.type;
    key = builder.key;
    value = builder.value;
    profile = Optional.ofNullable(builder.profile).orElse(SpringProfile.DEFAULT);
  }

  public static SpringPropertyKeyBuilder builder(SpringPropertyType type) {
    return new SpringPropertyBuilder(type);
  }

  @Override
  public SpringPropertyType type() {
    return type;
  }

  public PropertyKey key() {
    return key;
  }

  public PropertyValue value() {
    return value;
  }

  @Override
  public String filename() {
    if (profile.isDefault()) {
      return type.filePrefix();
    }

    return type.filePrefix() + "-" + profile.get();
  }

  private static final class SpringPropertyBuilder
    implements SpringPropertyKeyBuilder, SpringPropertyValueBuilder, SpringPropertyProfileBuilder
  {

    private final SpringPropertyType type;
    private @Nullable PropertyKey key;
    private @Nullable PropertyValue value;
    private @Nullable SpringProfile profile;

    private SpringPropertyBuilder(SpringPropertyType type) {
      Assert.notNull("type", type);

      this.type = type;
    }

    @Override
    public SpringPropertyValueBuilder key(PropertyKey key) {
      this.key = key;

      return this;
    }

    @Override
    public SpringPropertyProfileBuilder value(PropertyValue value) {
      this.value = value;

      return this;
    }

    @Override
    public SpringPropertyProfileBuilder profile(SpringProfile profile) {
      this.profile = profile;

      return this;
    }

    @Override
    public SpringProperty build() {
      return new SpringProperty(this);
    }
  }

  public interface SpringPropertyKeyBuilder {
    SpringPropertyValueBuilder key(PropertyKey key);
  }

  public interface SpringPropertyValueBuilder {
    SpringPropertyProfileBuilder value(PropertyValue value);
  }

  public interface SpringPropertyProfileBuilder {
    SpringPropertyProfileBuilder profile(SpringProfile profile);

    SpringProperty build();
  }
}
