package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocokta.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocokta.domain.SpringdocOktaModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringdocOktaApplicationService {

  private final SpringdocOktaModuleFactory springdocOkta;

  public SpringdocOktaApplicationService() {
    springdocOkta = new SpringdocOktaModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return springdocOkta.buildModule(properties);
  }
}
