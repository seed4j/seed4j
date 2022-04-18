package tech.jhipster.lite.generator.client.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.FileUtils.read;
import static tech.jhipster.lite.generator.project.domain.Constants.TSCONFIG_JSON;

import java.io.IOException;
import tech.jhipster.lite.common.domain.JsonUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record ClientCommonDomainService(ProjectRepository projectRepository) implements ClientCommonService {
  @Override
  public void excludeInTsconfigJson(Project project, String value) {
    String tsConfigFilePath = getPath(project.getFolder(), TSCONFIG_JSON);
    String tsConfigContent;
    try {
      tsConfigContent = read(tsConfigFilePath);
    } catch (IOException e) {
      throw new GeneratorException("Cannot read tsconfig " + tsConfigFilePath + ": " + e.getMessage());
    }

    String updatedTsConfigContent = JsonUtils.addValueInArray("exclude", value, tsConfigContent);
    projectRepository.write(project, updatedTsConfigContent, ".", TSCONFIG_JSON);
  }
}
