package com.seed4j.generator.prettier.application;

import com.seed4j.generator.prettier.domain.PrettierModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PrettierApplicationService {

  private final PrettierModuleFactory prettier;

  public PrettierApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.prettier = new PrettierModuleFactory(nodeLazyPackagesInstaller);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return prettier.buildModule(properties);
  }
}
