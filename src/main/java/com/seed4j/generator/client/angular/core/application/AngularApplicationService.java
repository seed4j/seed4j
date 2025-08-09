package com.seed4j.generator.client.angular.core.application;

import com.seed4j.generator.client.angular.core.domain.AngularModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AngularApplicationService {

  private final AngularModuleFactory angular;

  public AngularApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    angular = new AngularModuleFactory(nodeLazyPackagesInstaller);
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return angular.buildModule(properties);
  }
}
