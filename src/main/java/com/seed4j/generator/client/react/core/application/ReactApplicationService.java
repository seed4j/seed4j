package com.seed4j.generator.client.react.core.application;

import com.seed4j.generator.client.react.core.domain.ReactModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ReactApplicationService {

  private final ReactModuleFactory react;

  public ReactApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    react = new ReactModuleFactory(nodeLazyPackagesInstaller);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return react.buildModule(properties);
  }
}
