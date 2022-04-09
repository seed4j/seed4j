package tech.jhipster.lite.generator.client.common.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.common.domain.ClientCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class ClientCommonApplicationService {

  private final ClientCommonService clientCommonService;

  public ClientCommonApplicationService(ClientCommonService clientCommonService) {
    this.clientCommonService = clientCommonService;
  }

  public void excludeInTsconfigJson(Project project, String value) {
    clientCommonService.excludeInTsconfigJson(project, value);
  }
}
