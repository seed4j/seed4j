package tech.jhipster.lite.generator.server.javatool.jacoco.domain;

import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class JacocoDomainService implements JacocoService {

  public static final String SOURCE = "server/javatool/jacoco";
  public static final String PATCH = "jacoco-check-coverage.patch";

  private final ProjectRepository projectRepository;

  public JacocoDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addCheckMinimumCoverage(Project project) {
    projectRepository.gitInit(project);
    projectRepository.add(project, SOURCE, PATCH, ".jhipster");
    projectRepository.gitApplyPatch(project, FileUtils.getPath(project.getFolder(), ".jhipster", PATCH));
  }
}
