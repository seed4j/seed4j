package com.seed4j.generator.client.vue.i18n.application;

import com.seed4j.generator.client.vue.i18n.domain.VueI18nModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueI18nApplicationService {

  private final VueI18nModuleFactory vueI18n;

  public VueI18nApplicationService() {
    vueI18n = new VueI18nModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return vueI18n.buildModule(properties);
  }
}
