package tech.jhipster.lite.generator.setup.codespaces.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.setup.codespaces.domain.CodespacesService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class CodespacesApplicationService {

  private final CodespacesService codespacesService;

  public CodespacesApplicationService(CodespacesService codespacesService) {
    this.codespacesService = codespacesService;
  }

  public void init(Project project) {
    codespacesService.init(project);
  }
}
