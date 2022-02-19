package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface EurekaService {
  void init(Project project);

  void addDependencies(Project project);
  void addProperties(Project project);
  void addDockerCompose(Project project);
}
