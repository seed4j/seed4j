package com.seed4j.generator.server.springboot.database.mongodb.application;

import com.seed4j.generator.server.springboot.database.mongodb.domain.MongoDbModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MongoDbApplicationService {

  private final MongoDbModuleFactory mongoDb;

  public MongoDbApplicationService(DockerImages dockerImages) {
    mongoDb = new MongoDbModuleFactory(dockerImages);
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return mongoDb.buildModule(properties);
  }
}
