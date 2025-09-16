package com.seed4j.generator.client.angular.admin.health.application;

import com.seed4j.generator.client.angular.admin.health.domain.AngularHealthModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularHealthApplicationService {

  private final AngularHealthModuleFactory angularHealth;

  public AngularHealthApplicationService() {
    angularHealth = new AngularHealthModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return angularHealth.buildModule(properties);
  }
}
