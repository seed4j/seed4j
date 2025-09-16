package com.seed4j.generator.server.springboot.database.mongodb.application;

import com.seed4j.generator.server.springboot.database.mongodb.domain.MongoDbModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MongoDbApplicationService {

  private final MongoDbModuleFactory mongoDb;

  public MongoDbApplicationService(DockerImages dockerImages) {
    mongoDb = new MongoDbModuleFactory(dockerImages);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return mongoDb.buildModule(properties);
  }
}
