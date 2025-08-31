package com.seed4j.project.domain.history;

import com.seed4j.project.domain.ProjectsRepository;
import com.seed4j.shared.error.domain.Assert;

public class ProjectActionsAppender {

  private final ProjectsRepository projects;

  public ProjectActionsAppender(ProjectsRepository projects) {
    Assert.notNull("projects", projects);

    this.projects = projects;
  }

  public void append(ProjectActionToAppend actionToAppend) {
    projects.save(actionToAppend.path(), actionToAppend.action());
  }
}
