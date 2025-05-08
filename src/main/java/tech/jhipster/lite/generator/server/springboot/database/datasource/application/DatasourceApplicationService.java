package tech.jhipster.lite.generator.server.springboot.database.datasource.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.datasource.domain.DatasourceModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class DatasourceApplicationService {

  private final DatasourceModuleFactory datasource;

  public DatasourceApplicationService(DockerImages dockerImages) {
    datasource = new DatasourceModuleFactory(dockerImages);
  }

  public JHipsterModule buildPostgreSQL(JHipsterModuleProperties properties) {
    return datasource.buildPostgreSQL(properties);
  }

  public JHipsterModule buildMariaDB(JHipsterModuleProperties properties) {
    return datasource.buildMariaDB(properties);
  }

  public JHipsterModule buildMsSQL(JHipsterModuleProperties properties) {
    return datasource.buildMsSQL(properties);
  }

  public JHipsterModule buildMySQL(JHipsterModuleProperties properties) {
    return datasource.buildMySQL(properties);
  }
}
