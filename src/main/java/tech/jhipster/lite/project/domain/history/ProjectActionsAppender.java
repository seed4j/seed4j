package tech.jhipster.lite.project.domain.history;

import tech.jhipster.lite.project.domain.ProjectsRepository;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ProjectActionsAppender {

  private final ProjectsRepository projects;

  public ProjectActionsAppender(ProjectsRepository projects) {
    Assert.notNull("projects", projects);

    this.projects = projects;
  }

  public void append(ProjectActionToAppend actionToAppend) {
    ProjectHistory history = projects.getHistory(actionToAppend.path());

    history.append(actionToAppend.action());

    projects.save(history);
  }
}
