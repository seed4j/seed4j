package com.seed4j.generator.client.angular.admin.health.application;

import com.seed4j.generator.client.angular.admin.health.domain.AngularHealthModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularHealthApplicationService {

  private final AngularHealthModuleFactory angularHealth;

  public AngularHealthApplicationService() {
    angularHealth = new AngularHealthModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return angularHealth.buildModule(properties);
  }
}
