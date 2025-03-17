package tech.jhipster.lite.generator.server.springboot.webflux.web.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.webflux.web.domain.SpringBootWebfluxModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootWebfluxApplicationService {

  private final SpringBootWebfluxModuleFactory springBootWebflux;

  public SpringBootWebfluxApplicationService() {
    springBootWebflux = new SpringBootWebfluxModuleFactory();
  }

  public JHipsterModule buildNettyModule(JHipsterModuleProperties properties) {
    return springBootWebflux.buildNettyModule(properties);
  }

  public JHipsterModule buildEmptyModule(JHipsterModuleProperties properties) {
    return springBootWebflux.buildEmptyModule(properties);
  }
}
