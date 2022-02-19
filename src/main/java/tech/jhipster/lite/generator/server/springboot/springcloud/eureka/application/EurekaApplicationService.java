package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain.EurekaService;

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
