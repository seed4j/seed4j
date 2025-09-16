package com.seed4j.generator.server.springboot.database.datasource.application;

import com.seed4j.generator.server.springboot.database.datasource.domain.DatasourceModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class DatasourceApplicationService {

  private final DatasourceModuleFactory datasource;

  public DatasourceApplicationService(DockerImages dockerImages) {
    datasource = new DatasourceModuleFactory(dockerImages);
  }

  public Seed4JModule buildPostgreSQL(Seed4JModuleProperties properties) {
    return datasource.buildPostgreSQL(properties);
  }

  public Seed4JModule buildMariaDB(Seed4JModuleProperties properties) {
    return datasource.buildMariaDB(properties);
  }

  public Seed4JModule buildMsSQL(Seed4JModuleProperties properties) {
    return datasource.buildMsSQL(properties);
  }

  public Seed4JModule buildMySQL(Seed4JModuleProperties properties) {
    return datasource.buildMySQL(properties);
  }
}
