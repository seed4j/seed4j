package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocauth0.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocauth0.domain.SpringdocAuth0ModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringdocAuth0ApplicationService {

  private final SpringdocAuth0ModuleFactory springdocAuth0;

  public SpringdocAuth0ApplicationService() {
    springdocAuth0 = new SpringdocAuth0ModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return springdocAuth0.buildModule(properties);
  }
}
