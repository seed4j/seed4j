package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class SpringComment implements SpringPropertyTypeFileName {

  private final SpringPropertyType type;
  private final PropertyKey key;
  private final Comment comment;
  private final SpringProfile profile;

  private SpringComment(SpringCommentBuilder builder) {
    Assert.notNull("key", builder.key);
    Assert.notNull("comment", builder.comment);

    type = builder.type;
    this.key = builder.key;
    this.comment = builder.comment;
    profile = Optional.ofNullable(builder.profile).orElse(SpringProfile.DEFAULT);
  }

  public static SpringCommentPropertyKeyBuilder builder(SpringPropertyType type) {
    return new SpringCommentBuilder(type);
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

  private static final class SpringCommentBuilder
    implements SpringCommentPropertyKeyBuilder, SpringCommentCommentBuilder, SpringCommentProfileBuilder
  {

    private final SpringPropertyType type;
    private @Nullable PropertyKey key;
    private @Nullable Comment comment;
    private @Nullable SpringProfile profile;

    private SpringCommentBuilder(SpringPropertyType type) {
      Assert.notNull("type", type);

      this.type = type;
    }

    @Override
    public SpringCommentCommentBuilder key(PropertyKey key) {
      this.key = key;

      return this;
    }

    @Override
    public SpringCommentProfileBuilder comment(Comment comment) {
      this.comment = comment;

      return this;
    }

    @Override
    public SpringCommentProfileBuilder profile(SpringProfile profile) {
      this.profile = profile;

      return this;
    }

    @Override
    public SpringComment build() {
      return new SpringComment(this);
    }
  }

  public interface SpringCommentPropertyKeyBuilder {
    SpringCommentCommentBuilder key(PropertyKey key);
  }

  public interface SpringCommentCommentBuilder {
    SpringCommentProfileBuilder comment(Comment comment);
  }

  public interface SpringCommentProfileBuilder {
    SpringCommentProfileBuilder profile(SpringProfile profile);

    SpringComment build();
  }
}
