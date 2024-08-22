package tech.jhipster.lite.generator.client.tools.reacti18n.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tools.reacti18n.domain.ReactI18nModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ReactI18nApplicationService {

  private final ReactI18nModuleFactory factory;

  public ReactI18nApplicationService() {
    factory = new ReactI18nModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
