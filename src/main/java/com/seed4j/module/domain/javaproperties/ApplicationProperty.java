package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class ApplicationProperty implements SpringPropertyTypeFileName {

  private final SpringPropertyType type;
  private final PropertyKey key;
  private final PropertyValue value;
  private final SpringProfile profile;

  private ApplicationProperty(ApplicationPropertyBuilder builder) {
    Assert.notNull("key", builder.key);
    Assert.notNull("value", builder.value);

    type = builder.type;
    key = builder.key;
    value = builder.value;
    profile = Optional.ofNullable(builder.profile).orElse(SpringProfile.DEFAULT);
  }

  public static ApplicationPropertyKeyBuilder builder(SpringPropertyType type) {
    return new ApplicationPropertyBuilder(type);
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

  private static final class ApplicationPropertyBuilder
    implements ApplicationPropertyKeyBuilder, ApplicationPropertyValueBuilder, ApplicationPropertyProfileBuilder
  {

    private final SpringPropertyType type;
    private @Nullable PropertyKey key;
    private @Nullable PropertyValue value;
    private @Nullable SpringProfile profile;

    private ApplicationPropertyBuilder(SpringPropertyType type) {
      Assert.notNull("type", type);

      this.type = type;
    }

    @Override
    public ApplicationPropertyValueBuilder key(PropertyKey key) {
      this.key = key;

      return this;
    }

    @Override
    public ApplicationPropertyProfileBuilder value(PropertyValue value) {
      this.value = value;

      return this;
    }

    @Override
    public ApplicationPropertyProfileBuilder profile(SpringProfile profile) {
      this.profile = profile;

      return this;
    }

    @Override
    public ApplicationProperty build() {
      return new ApplicationProperty(this);
    }
  }

  public interface ApplicationPropertyKeyBuilder {
    ApplicationPropertyValueBuilder key(PropertyKey key);
  }

  public interface ApplicationPropertyValueBuilder {
    ApplicationPropertyProfileBuilder value(PropertyValue value);
  }

  public interface ApplicationPropertyProfileBuilder {
    ApplicationPropertyProfileBuilder profile(SpringProfile profile);

    ApplicationProperty build();
  }
}
