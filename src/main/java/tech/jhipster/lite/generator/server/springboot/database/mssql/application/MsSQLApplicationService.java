package tech.jhipster.lite.generator.server.springboot.database.mssql.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.database.mssql.domain.MsSQLService;

@Service
public class MsSQLApplicationService {

  private final MsSQLService msSQLService;

  public MsSQLApplicationService(MsSQLService msSQLService) {
    this.msSQLService = msSQLService;
  }

  public void init(Project project) {
    msSQLService.init(project);
  }
}
