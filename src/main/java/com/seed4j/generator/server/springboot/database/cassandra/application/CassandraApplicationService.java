package com.seed4j.generator.server.springboot.database.cassandra.application;

import com.seed4j.generator.server.springboot.database.cassandra.domain.CassandraModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CassandraApplicationService {

  private final CassandraModuleFactory cassandra;

  public CassandraApplicationService(DockerImages dockerImages) {
    cassandra = new CassandraModuleFactory(dockerImages);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return cassandra.buildModule(properties);
  }
}
