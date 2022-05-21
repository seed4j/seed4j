package tech.jhipster.lite.generator.server.springboot.webflux.web.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface SpringBootWebfluxService {
  void init(Project project);

  void addSpringBootWebflux(Project project);

  void addSpringBootActuator(Project project);

  void addExceptionHandler(Project project);
}
