package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.domain.MongockModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MongockApplicationService {

  private final MongockModuleFactory mongock;

  public MongockApplicationService() {
    mongock = new MongockModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return mongock.buildModule(properties);
  }
}
