package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocoauth.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocoauth.domain.SpringdocOauth2ModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringdocOauth2ApplicationService {

  private final SpringdocOauth2ModuleFactory springdocOauth2;

  public SpringdocOauth2ApplicationService() {
    springdocOauth2 = new SpringdocOauth2ModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return springdocOauth2.buildModule(properties);
  }
}
