package tech.jhipster.lite.generator.server.springboot.database.mongodb.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.MongoDbModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MongodbApplicationService {

  private final MongoDbModuleFactory factory;

  public MongodbApplicationService(DockerImages dockerImages) {
    factory = new MongoDbModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
