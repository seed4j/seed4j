package com.seed4j.generator.client.vue.i18n.application;

import com.seed4j.generator.client.vue.i18n.domain.VueI18nModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueI18nApplicationService {

  private final VueI18nModuleFactory vueI18n;

  public VueI18nApplicationService() {
    vueI18n = new VueI18nModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return vueI18n.buildModule(properties);
  }
}
