package tech.jhipster.lite.generator.typescript.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.typescript.domain.TypescriptService;

@Service
public class TypescriptApplicationService {

  private final TypescriptService typescriptService;

  public TypescriptApplicationService(TypescriptService typescriptService) {
    this.typescriptService = typescriptService;
  }

  public void init(Project project) {
    typescriptService.init(project);
  }
}
