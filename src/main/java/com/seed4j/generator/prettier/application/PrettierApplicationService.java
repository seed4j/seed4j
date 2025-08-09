package com.seed4j.generator.prettier.application;

import com.seed4j.generator.prettier.domain.PrettierModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PrettierApplicationService {

  private final PrettierModuleFactory prettier;

  public PrettierApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.prettier = new PrettierModuleFactory(nodeLazyPackagesInstaller);
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return prettier.buildModule(properties);
  }
}
