package tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface ConsulService {
  void init(Project project);

  void addDependencies(Project project);
  void addProperties(Project project);
  void addDockerConsul(Project project);
}
