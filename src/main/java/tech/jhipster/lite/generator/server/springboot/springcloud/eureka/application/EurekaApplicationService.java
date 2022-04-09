package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain.EurekaService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class EurekaApplicationService {

  private final EurekaService eurekaService;

  public EurekaApplicationService(EurekaService eurekaService) {
    this.eurekaService = eurekaService;
  }

  public void init(Project project) {
    eurekaService.init(project);
  }
}
