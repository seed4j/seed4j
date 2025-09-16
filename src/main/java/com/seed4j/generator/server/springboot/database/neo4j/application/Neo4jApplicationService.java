package com.seed4j.generator.server.springboot.database.neo4j.application;

import com.seed4j.generator.server.springboot.database.neo4j.domain.Neo4jModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class Neo4jApplicationService {

  private final Neo4jModuleFactory neo4j;

  public Neo4jApplicationService(DockerImages dockerImages) {
    neo4j = new Neo4jModuleFactory(dockerImages);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return neo4j.buildModule(properties);
  }
}
