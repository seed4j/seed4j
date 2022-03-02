package tech.jhipster.lite.generator.server.springboot.database.cassandra.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.database.cassandra.domain.CassandraService;

@Service
public class CassandraApplicationService {

  private final CassandraService cassandraService;

  public CassandraApplicationService(CassandraService cassandraService) {
    this.cassandraService = cassandraService;
  }

  public void init(Project project) {
    this.cassandraService.init(project);
  }
}
