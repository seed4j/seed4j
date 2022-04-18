package tech.jhipster.lite.generator.server.springboot.springcloud.consul.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.ConsulService;

@Service
public record ConsulApplicationService(ConsulService consulService) {
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
