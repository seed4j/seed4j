package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class ApplicationPropertyComment implements SpringPropertyTypeFileName {

  private final SpringPropertyType type;
  private final PropertyKey key;
  private final Comment comment;
  private final SpringProfile profile;

  private ApplicationPropertyComment(ApplicationPropertyCommentBuilder builder) {
    Assert.notNull("key", builder.key);
    Assert.notNull("comment", builder.comment);

    type = builder.type;
    this.key = builder.key;
    this.comment = builder.comment;
    profile = Optional.ofNullable(builder.profile).orElse(SpringProfile.DEFAULT);
  }

  public static ApplicationPropertyCommentKeyBuilder builder(SpringPropertyType type) {
    return new ApplicationPropertyCommentBuilder(type);
  }

  @Override
  public SpringPropertyType type() {
    return type;
  }

  public PropertyKey key() {
    return key;
  }

  public Comment value() {
    return comment;
  }

  @Override
  public String filename() {
    if (profile.isDefault()) {
      return type.filePrefix();
    }

    return type.filePrefix() + "-" + profile.get();
  }

  private static final class ApplicationPropertyCommentBuilder
    implements ApplicationPropertyCommentKeyBuilder, ApplicationPropertyCommentCommentBuilder, ApplicationPropertyCommentProfileBuilder
  {

    private final SpringPropertyType type;
    private @Nullable PropertyKey key;
    private @Nullable Comment comment;
    private @Nullable SpringProfile profile;

    private ApplicationPropertyCommentBuilder(SpringPropertyType type) {
      Assert.notNull("type", type);

      this.type = type;
    }

    @Override
    public ApplicationPropertyCommentCommentBuilder key(PropertyKey key) {
      this.key = key;

      return this;
    }

    @Override
    public ApplicationPropertyCommentProfileBuilder comment(Comment comment) {
      this.comment = comment;

      return this;
    }

    @Override
    public ApplicationPropertyCommentProfileBuilder profile(SpringProfile profile) {
      this.profile = profile;

      return this;
    }

    @Override
    public ApplicationPropertyComment build() {
      return new ApplicationPropertyComment(this);
    }
  }

  public interface ApplicationPropertyCommentKeyBuilder {
    ApplicationPropertyCommentCommentBuilder key(PropertyKey key);
  }

  public interface ApplicationPropertyCommentCommentBuilder {
    ApplicationPropertyCommentProfileBuilder comment(Comment comment);
  }

  public interface ApplicationPropertyCommentProfileBuilder {
    ApplicationPropertyCommentProfileBuilder profile(SpringProfile profile);

    ApplicationPropertyComment build();
  }
}
