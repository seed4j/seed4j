package tech.jhipster.lite.generator.client.tools.cypress.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tools.cypress.domain.CypressService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class CypressApplicationService {

  private final CypressService cypressService;

  public CypressApplicationService(CypressService cypressService) {
    this.cypressService = cypressService;
  }

  public void init(Project project) {
    cypressService.init(project);
  }
}
