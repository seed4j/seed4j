package com.seed4j.project.infrastructure.primary;

import com.seed4j.module.domain.JHipsterModuleApplied;
import com.seed4j.project.application.ProjectsApplicationService;
import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.history.ProjectAction;
import com.seed4j.project.domain.history.ProjectActionToAppend;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.stereotype.Service;

@Service
public class JavaProjects {

  private final ProjectsApplicationService projects;

  public JavaProjects(ProjectsApplicationService projects) {
    this.projects = projects;
  }

  public void moduleApplied(JHipsterModuleApplied moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    projects.append(projectActionToAdd(moduleApplied));
  }

  private static ProjectActionToAppend projectActionToAdd(JHipsterModuleApplied moduleApplied) {
    ProjectPath path = new ProjectPath(moduleApplied.properties().projectFolder().get());

    ProjectAction action = ProjectAction.builder()
      .module(moduleApplied.slug().get())
      .date(moduleApplied.time())
      .parameters(moduleApplied.properties().getParameters());

    return new ProjectActionToAppend(path, action);
  }
}
