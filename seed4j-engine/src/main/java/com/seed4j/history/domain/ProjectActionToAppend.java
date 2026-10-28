package com.seed4j.history.domain;

import com.seed4j.shared.error.domain.Assert;

public record ProjectActionToAppend(ProjectPath path, ProjectAction action) {
  public ProjectActionToAppend {
    Assert.notNull("path", path);
    Assert.notNull("action", action);
  }
}
