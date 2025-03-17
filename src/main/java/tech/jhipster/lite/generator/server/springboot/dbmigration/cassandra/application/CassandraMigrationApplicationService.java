package tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.domain.CassandraMigrationModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class CassandraMigrationApplicationService {

  private final CassandraMigrationModuleFactory cassandraMigration;

  public CassandraMigrationApplicationService(DockerImages dockerImages) {
    cassandraMigration = new CassandraMigrationModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return cassandraMigration.buildModule(properties);
  }
}
