package tech.jhipster.lite.module.domain.javaproperties;

import java.util.Map;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringPropertiesBlockComment implements SpringPropertyTypeFileName {

  private final SpringPropertyType type;
  private final Comment comment;
  private final Map<PropertyKey, PropertyValue> properties;
  private final SpringProfile profile;

  public SpringPropertiesBlockComment(SpringPropertiesBlockCommentBuilder builder) {
    Assert.notNull("comment", builder.comment);
    Assert.notNull("properties", builder.properties);

    type = builder.type;
    this.comment = builder.comment;
    this.properties = builder.properties;
    profile = buildProfile(builder.profile);
  }

  private SpringProfile buildProfile(SpringProfile profile) {
    if (profile == null) {
      return SpringProfile.DEFAULT;
    }

    return profile;
  }

  public static SpringPropertiesBlockCommentBuilder builder(SpringPropertyType type) {
    return new SpringPropertiesBlockCommentBuilder(type);
  }

  @Override
  public SpringPropertyType type() {
    return type;
  }

  public Comment comment() {
    return comment;
  }

  public Map<PropertyKey, PropertyValue> properties() {
    return properties;
  }

  @Override
  public String filename() {
    if (profile.isDefault()) {
      return type.filePrefix();
    }

    return type.filePrefix() + "-" + profile.get();
  }

  public static class SpringPropertiesBlockCommentBuilder
    implements
      SpringPropertiesBlockCommentCommentBuilder,
      SpringPropertiesBlockCommentPropertiesBuilder,
      SpringPropertiesBlockCommentProfileBuilder {

    private final SpringPropertyType type;
    private Comment comment;
    private Map<PropertyKey, PropertyValue> properties;
    private SpringProfile profile;

    private SpringPropertiesBlockCommentBuilder(SpringPropertyType type) {
      Assert.notNull("type", type);

      this.type = type;
    }

    @Override
    public SpringPropertiesBlockCommentPropertiesBuilder comment(Comment comment) {
      this.comment = comment;

      return this;
    }

    @Override
    public SpringPropertiesBlockCommentProfileBuilder properties(Map<PropertyKey, PropertyValue> properties) {
      this.properties = properties;

      return this;
    }

    @Override
    public SpringPropertiesBlockCommentProfileBuilder profile(SpringProfile profile) {
      this.profile = profile;

      return this;
    }

    @Override
    public SpringPropertiesBlockComment build() {
      return new SpringPropertiesBlockComment(this);
    }
  }

  public interface SpringPropertiesBlockCommentCommentBuilder {
    SpringPropertiesBlockCommentPropertiesBuilder comment(Comment comment);
  }

  public interface SpringPropertiesBlockCommentPropertiesBuilder {
    SpringPropertiesBlockCommentProfileBuilder properties(Map<PropertyKey, PropertyValue> properties);
  }

  public interface SpringPropertiesBlockCommentProfileBuilder {
    SpringPropertiesBlockCommentProfileBuilder profile(SpringProfile profile);

    SpringPropertiesBlockComment build();
  }
}
