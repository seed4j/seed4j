package tech.jhipster.lite.generator.server.springboot.docker.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.docker.domain.SpringBootDockerService;

@Service
public class SpringBootDockerApplicationService {

  private final SpringBootDockerService springBootDockerService;

  public SpringBootDockerApplicationService(SpringBootDockerService springBootDockerService) {
    this.springBootDockerService = springBootDockerService;
  }

  public void addJib(Project project) {
    this.springBootDockerService.addJib(project);
  }

  public void addDockerFile(Project project) {
    this.springBootDockerService.addDockerFile(project);
  }

  public void addJibPlugin(Project project) {
    this.springBootDockerService.addJibPlugin(project);
  }

  public void addJibFiles(Project project) {
    this.springBootDockerService.addJibFiles(project);
  }
}
