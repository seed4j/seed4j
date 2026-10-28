package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;

public record AddJavaBuildProfile(BuildProfileId buildProfileId, Optional<BuildProfileActivation> activation) implements JavaBuildCommand {
  public AddJavaBuildProfile {
    Assert.notNull("buildProfileId", buildProfileId);
    Assert.notNull("activation", activation);
  }

  public AddJavaBuildProfile(BuildProfileId buildProfileId) {
    this(buildProfileId, Optional.empty());
  }

  public AddJavaBuildProfile(BuildProfileId buildProfileId, BuildProfileActivation activation) {
    this(buildProfileId, Optional.of(activation));
  }
}
