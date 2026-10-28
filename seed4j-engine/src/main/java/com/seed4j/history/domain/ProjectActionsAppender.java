package com.seed4j.history.domain;

import com.seed4j.shared.error.domain.Assert;

public class ProjectActionsAppender {

  private final ProjectHistoryRepository projects;

  public ProjectActionsAppender(ProjectHistoryRepository projects) {
    Assert.notNull("projects", projects);

    this.projects = projects;
  }

  public void append(ProjectActionToAppend actionToAppend) {
    projects.save(actionToAppend.path(), actionToAppend.action());
  }
}
