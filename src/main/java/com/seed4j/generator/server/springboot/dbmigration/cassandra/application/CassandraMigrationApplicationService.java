package com.seed4j.generator.server.springboot.dbmigration.cassandra.application;

import com.seed4j.generator.server.springboot.dbmigration.cassandra.domain.CassandraMigrationModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

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
