package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfigClientService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class SpringCloudConfigClientApplicationService {

  private final SpringCloudConfigClientService springCloudConfigClientService;

  public SpringCloudConfigClientApplicationService(SpringCloudConfigClientService springCloudConfigClientService) {
    this.springCloudConfigClientService = springCloudConfigClientService;
  }

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
