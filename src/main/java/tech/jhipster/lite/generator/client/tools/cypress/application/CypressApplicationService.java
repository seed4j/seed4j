package tech.jhipster.lite.generator.client.tools.cypress.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tools.cypress.domain.CypressService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record CypressApplicationService(CypressService cypressService) {
  public void init(Project project) {
    cypressService.init(project);
  }
}
