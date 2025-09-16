package com.seed4j.generator.server.springboot.dbmigration.cassandra.application;

import com.seed4j.generator.server.springboot.dbmigration.cassandra.domain.CassandraMigrationModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CassandraMigrationApplicationService {

  private final CassandraMigrationModuleFactory cassandraMigration;

  public CassandraMigrationApplicationService(DockerImages dockerImages) {
    cassandraMigration = new CassandraMigrationModuleFactory(dockerImages);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return cassandraMigration.buildModule(properties);
  }
}
