package tech.jhipster.lite.generator.client.common.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.common.domain.ClientCommonService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record ClientCommonApplicationService(ClientCommonService clientCommonService) {
  public void excludeInTsconfigJson(Project project, String value) {
    clientCommonService.excludeInTsconfigJson(project, value);
  }
}
