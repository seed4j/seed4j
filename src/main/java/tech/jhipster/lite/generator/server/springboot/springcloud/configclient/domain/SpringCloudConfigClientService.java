package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface SpringCloudConfigClientService {
  void init(Project project);

  void addDockerCompose(Project project);

  void addProperties(Project project);

  void addCloudConfigDependencies(Project project);
}
