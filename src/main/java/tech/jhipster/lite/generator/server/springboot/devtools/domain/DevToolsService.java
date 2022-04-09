package tech.jhipster.lite.generator.server.springboot.devtools.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface DevToolsService {
  void init(Project project);

  void addSpringBootDevTools(Project project);
  void addProperties(Project project);
}
