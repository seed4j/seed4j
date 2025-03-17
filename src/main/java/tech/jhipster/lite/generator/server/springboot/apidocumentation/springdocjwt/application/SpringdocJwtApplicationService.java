package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocjwt.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocjwt.domain.SpringdocJwtModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringdocJwtApplicationService {

  private final SpringdocJwtModuleFactory springdocJwt;

  public SpringdocJwtApplicationService() {
    springdocJwt = new SpringdocJwtModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return springdocJwt.buildModule(properties);
  }
}
