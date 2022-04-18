package tech.jhipster.lite.generator.setup.codespaces.domain;

import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record CodespacesDomainService(ProjectRepository projectRepository) implements CodespacesService {
  public static final String SOURCE = "setup/codespaces";
  public static final String DEVCONTAINER_DEST = ".devcontainer";

  @Override
  public void init(Project project) {
    addConfig(project);
    addDockerfile(project);
  }

  private void addConfig(Project project) {
    projectRepository.add(project, SOURCE, "devcontainer.json", DEVCONTAINER_DEST, "devcontainer.json");
  }

  private void addDockerfile(Project project) {
    projectRepository.add(project, SOURCE, "Dockerfile", DEVCONTAINER_DEST, "Dockerfile");
  }
}
