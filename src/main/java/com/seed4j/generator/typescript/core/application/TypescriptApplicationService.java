package com.seed4j.generator.typescript.core.application;

import com.seed4j.generator.typescript.core.domain.TypescriptModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TypescriptApplicationService {

  private final TypescriptModuleFactory typescript;

  public TypescriptApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.typescript = new TypescriptModuleFactory(nodeLazyPackagesInstaller);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties project) {
    return typescript.buildModule(project);
  }
}
