package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfigClientService;

@Service
public record SpringCloudConfigClientApplicationService(SpringCloudConfigClientService springCloudConfigClientService) {
  public void init(Project project) {
    this.springCloudConfigClientService.init(project);
  }

  public void addDependencies(Project project) {
    this.springCloudConfigClientService.addCloudConfigDependencies(project);
  }

  public void addDockerCompose(Project project) {
    this.springCloudConfigClientService.addDockerCompose(project);
  }

  public void addProperties(Project project) {
    this.springCloudConfigClientService.addProperties(project);
  }
}
