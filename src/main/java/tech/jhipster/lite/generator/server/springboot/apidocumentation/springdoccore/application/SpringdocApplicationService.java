package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoccore.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoccore.domain.SpringdocModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringdocApplicationService {

  private final SpringdocModuleFactory springdoc;

  public SpringdocApplicationService() {
    springdoc = new SpringdocModuleFactory();
  }

  public JHipsterModule buildSpringdocMvcModule(JHipsterModuleProperties properties) {
    return springdoc.buildModuleForMvc(properties);
  }

  public JHipsterModule buildSpringdocWebfluxModule(JHipsterModuleProperties properties) {
    return springdoc.buildModuleForWebflux(properties);
  }
}
