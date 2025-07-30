package com.seed4j.generator.server.springboot.database.cassandra.application;

import com.seed4j.generator.server.springboot.database.cassandra.domain.CassandraModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CassandraApplicationService {

  private final CassandraModuleFactory cassandra;

  public CassandraApplicationService(DockerImages dockerImages) {
    cassandra = new CassandraModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return cassandra.buildModule(properties);
  }
}
