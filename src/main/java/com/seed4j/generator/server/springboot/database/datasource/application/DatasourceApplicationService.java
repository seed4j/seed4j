package com.seed4j.generator.server.springboot.database.datasource.application;

import com.seed4j.generator.server.springboot.database.datasource.domain.DatasourceModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class DatasourceApplicationService {

  private final DatasourceModuleFactory datasource;

  public DatasourceApplicationService(DockerImages dockerImages) {
    datasource = new DatasourceModuleFactory(dockerImages);
  }

  public JHipsterModule buildPostgreSQL(SeedModuleProperties properties) {
    return datasource.buildPostgreSQL(properties);
  }

  public JHipsterModule buildMariaDB(SeedModuleProperties properties) {
    return datasource.buildMariaDB(properties);
  }

  public JHipsterModule buildMsSQL(SeedModuleProperties properties) {
    return datasource.buildMsSQL(properties);
  }

  public JHipsterModule buildMySQL(SeedModuleProperties properties) {
    return datasource.buildMySQL(properties);
  }
}
