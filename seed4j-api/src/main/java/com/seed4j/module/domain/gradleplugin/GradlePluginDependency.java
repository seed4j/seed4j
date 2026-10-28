package com.seed4j.module.domain.gradleplugin;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.shared.error.domain.Assert;

public record GradlePluginDependency(GroupId groupId, ArtifactId artifactId) {
  public GradlePluginDependency {
    Assert.notNull("groupId", groupId);
    Assert.notNull("artifactId", artifactId);
  }
}
