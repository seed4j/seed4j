package com.seed4j.generator.client.angular.i18n.application;

import com.seed4j.generator.client.angular.i18n.domain.AngularI18nModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularI18nApplicationService {

  private final AngularI18nModuleFactory angulari18n;

  public AngularI18nApplicationService() {
    angulari18n = new AngularI18nModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return angulari18n.buildModule(properties);
  }
}
