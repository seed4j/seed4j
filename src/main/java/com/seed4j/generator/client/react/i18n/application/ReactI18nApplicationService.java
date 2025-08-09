package com.seed4j.generator.client.react.i18n.application;

import com.seed4j.generator.client.react.i18n.domain.ReactI18nModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ReactI18nApplicationService {

  private final ReactI18nModuleFactory reactI18n;

  public ReactI18nApplicationService() {
    reactI18n = new ReactI18nModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return reactI18n.buildModule(properties);
  }
}
