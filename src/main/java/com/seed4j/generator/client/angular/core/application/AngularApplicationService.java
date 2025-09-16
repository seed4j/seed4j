package com.seed4j.generator.client.angular.core.application;

import com.seed4j.generator.client.angular.core.domain.AngularModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularApplicationService {

  private final AngularModuleFactory angular;

  public AngularApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    angular = new AngularModuleFactory(nodeLazyPackagesInstaller);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return angular.buildModule(properties);
  }
}
