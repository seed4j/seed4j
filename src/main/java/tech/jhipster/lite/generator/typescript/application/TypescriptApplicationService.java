package tech.jhipster.lite.generator.typescript.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.typescript.domain.TypescriptService;

@Service
public record TypescriptApplicationService(TypescriptService typescriptService) {
  public void init(Project project) {
    typescriptService.init(project);
  }
}
