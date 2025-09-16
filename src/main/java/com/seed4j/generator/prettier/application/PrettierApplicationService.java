package com.seed4j.generator.prettier.application;

import com.seed4j.generator.prettier.domain.PrettierModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PrettierApplicationService {

  private final PrettierModuleFactory prettier;

  public PrettierApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.prettier = new PrettierModuleFactory(nodeLazyPackagesInstaller);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return prettier.buildModule(properties);
  }
}
