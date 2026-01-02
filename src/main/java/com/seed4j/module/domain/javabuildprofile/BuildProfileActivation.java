package com.seed4j.module.domain.javabuildprofile;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class BuildProfileActivation {

  private final Optional<Boolean> activeByDefault;

  public BuildProfileActivation(BuildProfileActivationBuilder builder) {
    this.activeByDefault = Optional.ofNullable(builder.activeByDefault);
  }

  public Optional<Boolean> activeByDefault() {
    return activeByDefault;
  }

  public static BuildProfileActivationBuilder builder() {
    return new BuildProfileActivationBuilder();
  }

  public static final class BuildProfileActivationBuilder {

    private @Nullable Boolean activeByDefault;

    private BuildProfileActivationBuilder() {}

    public BuildProfileActivationBuilder activeByDefault() {
      return activeByDefault(true);
    }

    public BuildProfileActivationBuilder activeByDefault(Boolean activeByDefault) {
      Assert.notNull("activeByDefault", activeByDefault);
      this.activeByDefault = activeByDefault;
      return this;
    }

    public BuildProfileActivation build() {
      return new BuildProfileActivation(this);
    }
  }
}
