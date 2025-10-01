package com.seed4j.generator.client.angular.tailwind.application;

import com.seed4j.generator.client.angular.tailwind.domain.AngularTailwindModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularTailwindApplicationService {

  private final AngularTailwindModuleFactory angularTailwind;

  public AngularTailwindApplicationService() {
    angularTailwind = new AngularTailwindModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return angularTailwind.buildModule(properties);
  }
}
