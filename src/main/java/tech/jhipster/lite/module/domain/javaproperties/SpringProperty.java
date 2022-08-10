package tech.jhipster.lite.module.domain.javaproperties;

import tech.jhipster.lite.error.domain.Assert;

public class SpringProperty {

  private final SpringPropertyType type;
  private final PropertyKey key;
  private final PropertyValue value;
  private final PropertyComment comment;
  private final SpringProfile profile;

  private SpringProperty(SpringPropertyBuilder builder) {
    Assert.notNull("key", builder.key);
    Assert.notNull("value", builder.value);

    type = builder.type;
    key = builder.key;
    value = builder.value;
    comment = builder.comment;
    profile = buildProfile(builder.profile);
  }

  private SpringProfile buildProfile(SpringProfile profile) {
    if (profile == null) {
      return SpringProfile.DEFAULT;
    }

    return profile;
  }

  public static SpringPropertyBuilder builder(SpringPropertyType type) {
    return new SpringPropertyBuilder(type);
  }

  public SpringPropertyType type() {
    return type;
  }

  public PropertyKey key() {
    return key;
  }

  public PropertyValue value() {
    return value;
  }

  public PropertyComment comment() {
    return comment;
  }

  public String filename() {
    if (profile.isDefault()) {
      return type.filePrefix();
    }

    return type.filePrefix() + "-" + profile.get();
  }

  public static class SpringPropertyBuilder
    implements SpringPropertyKeyBuilder, SpringPropertyValueBuilder, SpringPropertyCommentBuilder, SpringPropertyProfileBuilder {

    private final SpringPropertyType type;
    private PropertyKey key;
    private PropertyValue value;
    private PropertyComment comment;
    private SpringProfile profile;

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
    public SpringPropertyCommentBuilder comment(PropertyComment comment) {
      this.comment = comment;

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

  public interface SpringPropertyCommentBuilder {
    SpringPropertyCommentBuilder comment(PropertyComment comment);
  }

  public interface SpringPropertyProfileBuilder {
    SpringPropertyProfileBuilder profile(SpringProfile profile);

    SpringProperty build();
  }
}
