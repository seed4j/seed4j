package tech.jhipster.lite.generator.server.springboot.springcloud.consul.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.ConsulService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class ConsulApplicationService {

  private final ConsulService consulService;

  public ConsulApplicationService(ConsulService consulService) {
    this.consulService = consulService;
  }

  public void init(Project project) {
    consulService.init(project);
  }

  public void addDependencies(Project project) {
    consulService.addDependencies(project);
  }

  public void addProperties(Project project) {
    consulService.addProperties(project);
  }

  public void addDockerConsul(Project project) {
    consulService.addDockerConsul(project);
  }
}
