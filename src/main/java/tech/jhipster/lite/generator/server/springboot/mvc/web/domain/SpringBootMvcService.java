package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface SpringBootMvcService {
  void init(Project project);

  void addSpringBootMvc(Project project);
  void addSpringBootUndertow(Project project);
  void addSpringBootActuator(Project project);
  void addExceptionHandler(Project project);
}
