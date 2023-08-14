package tech.jhipster.lite.project.infrastructure.primary;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;
import tech.jhipster.lite.project.application.ProjectsApplicationService;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.project.domain.history.ProjectAction;
import tech.jhipster.lite.project.domain.history.ProjectActionToAppend;
import tech.jhipster.lite.shared.error.domain.Assert;

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

    ProjectAction action = ProjectAction
      .builder()
      .module(moduleApplied.slug().get())
      .date(moduleApplied.time())
      .parameters(moduleApplied.properties().getParameters());

    return new ProjectActionToAppend(path, action);
  }
}
