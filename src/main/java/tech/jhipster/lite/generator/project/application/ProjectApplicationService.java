package tech.jhipster.lite.generator.project.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.ProjectService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class ProjectApplicationService {

  private final ProjectService projectService;

  public ProjectApplicationService(ProjectService projectService) {
    this.projectService = projectService;
  }

  public void init(Project project) {
    projectService.init(project);
  }

  public void addPackageJson(Project project) {
    projectService.addPackageJson(project);
  }

  public void addReadme(Project project) {
    projectService.addReadme(project);
  }

  public void addGitConfiguration(Project project) {
    projectService.addGitConfiguration(project);
  }

  public void addEditorConfiguration(Project project) {
    projectService.addEditorConfiguration(project);
  }

  public void addPrettier(Project project) {
    projectService.addPrettier(project);
  }

  public void gitInit(Project project) {
    projectService.gitInit(project);
  }

  public byte[] download(Project project) {
    return projectService.download(project);
  }
}
