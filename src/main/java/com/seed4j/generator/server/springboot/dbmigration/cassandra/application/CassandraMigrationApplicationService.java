package com.seed4j.generator.server.springboot.dbmigration.cassandra.application;

import com.seed4j.generator.server.springboot.dbmigration.cassandra.domain.CassandraMigrationModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CassandraMigrationApplicationService {

  private final CassandraMigrationModuleFactory cassandraMigration;

  public CassandraMigrationApplicationService(DockerImages dockerImages) {
    cassandraMigration = new CassandraMigrationModuleFactory(dockerImages);
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return cassandraMigration.buildModule(properties);
  }
}
