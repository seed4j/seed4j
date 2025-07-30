package com.seed4j.project.domain.history;

import com.seed4j.project.domain.ProjectPath;
import com.seed4j.shared.error.domain.Assert;

public record ProjectActionToAppend(ProjectPath path, ProjectAction action) {
  public ProjectActionToAppend {
    Assert.notNull("path", path);
    Assert.notNull("action", action);
  }
}
