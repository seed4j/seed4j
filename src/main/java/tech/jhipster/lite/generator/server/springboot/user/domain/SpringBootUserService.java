package tech.jhipster.lite.generator.server.springboot.user.domain;

import tech.jhipster.lite.generator.tools.domain.DatabaseType;
import tech.jhipster.lite.generator.tools.domain.Project;

public interface SpringBootUserService {
  void addUserAndAuthorityEntities(Project project, DatabaseType sqlDatabase);
}
