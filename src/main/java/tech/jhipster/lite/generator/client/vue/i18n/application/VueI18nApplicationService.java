package tech.jhipster.lite.generator.client.vue.i18n.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.vue.i18n.domain.VueI18nModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class VueI18nApplicationService {

  private final VueI18nModuleFactory vueI18n;

  public VueI18nApplicationService() {
    vueI18n = new VueI18nModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return vueI18n.buildModule(properties);
  }
}
