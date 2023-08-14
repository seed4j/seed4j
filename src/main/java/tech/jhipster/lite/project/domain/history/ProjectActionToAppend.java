package tech.jhipster.lite.project.domain.history;

import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.shared.error.domain.Assert;

public record ProjectActionToAppend(ProjectPath path, ProjectAction action) {
  public ProjectActionToAppend {
    Assert.notNull("path", path);
    Assert.notNull("action", action);
  }
}
