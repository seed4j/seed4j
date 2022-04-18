package tech.jhipster.lite.generator.setup.codespaces.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.setup.codespaces.domain.CodespacesService;

@Service
public record CodespacesApplicationService(CodespacesService codespacesService) {
  public void init(Project project) {
    codespacesService.init(project);
  }
}
