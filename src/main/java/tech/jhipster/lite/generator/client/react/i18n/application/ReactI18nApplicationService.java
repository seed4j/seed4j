package tech.jhipster.lite.generator.client.react.i18n.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.react.i18n.domain.ReactI18nModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ReactI18nApplicationService {

  private final ReactI18nModuleFactory reactI18n;

  public ReactI18nApplicationService() {
    reactI18n = new ReactI18nModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return reactI18n.buildModule(properties);
  }
}
