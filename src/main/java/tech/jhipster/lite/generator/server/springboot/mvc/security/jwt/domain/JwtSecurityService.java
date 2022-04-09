package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface JwtSecurityService {
  void init(Project project);
  void addBasicAuth(Project project);
  void addLoggerInConfiguration(Project project);
}
