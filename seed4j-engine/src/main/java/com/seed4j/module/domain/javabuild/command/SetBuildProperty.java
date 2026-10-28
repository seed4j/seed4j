package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.buildproperties.BuildProperty;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;

public record SetBuildProperty(BuildProperty property, Optional<BuildProfileId> buildProfile) implements JavaBuildCommand {
  public SetBuildProperty {
    Assert.notNull("property", property);
    Assert.notNull("buildProfile", buildProfile);
  }

  public SetBuildProperty(BuildProperty property) {
    this(property, Optional.empty());
  }

  public SetBuildProperty(BuildProperty property, BuildProfileId buildProfile) {
    this(property, Optional.of(buildProfile));
  }
}
