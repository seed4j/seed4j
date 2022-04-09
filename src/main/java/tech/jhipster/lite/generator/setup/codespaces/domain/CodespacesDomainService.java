package tech.jhipster.lite.generator.setup.codespaces.domain;

import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class CodespacesDomainService implements CodespacesService {

  public static final String SOURCE = "setup/codespaces";
  public static final String DEVCONTAINER_DEST = ".devcontainer";

  private final ProjectRepository projectRepository;

  public CodespacesDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

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
