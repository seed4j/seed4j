package tech.jhipster.lite.generator.server.springboot.database.mongodb.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.MongoDbModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MongodbApplicationService {

  private final MongoDbModuleFactory mongoDb;

  public MongodbApplicationService(DockerImages dockerImages) {
    mongoDb = new MongoDbModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return mongoDb.buildModule(properties);
  }
}
