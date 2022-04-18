package tech.jhipster.lite.generator.server.javatool.jacoco.domain;

import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record JacocoDomainService(ProjectRepository projectRepository) implements JacocoService {
  public static final String SOURCE = "server/javatool/jacoco";
  public static final String PATCH = "jacoco-check-coverage.patch";

  @Override
  public void addCheckMinimumCoverage(Project project) {
    projectRepository.gitInit(project);
    projectRepository.add(project, SOURCE, PATCH, ".jhipster");
    projectRepository.gitApplyPatch(project, FileUtils.getPath(project.getFolder(), ".jhipster", PATCH));
  }
}
