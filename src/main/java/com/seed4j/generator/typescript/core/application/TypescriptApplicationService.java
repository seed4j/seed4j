package com.seed4j.generator.typescript.core.application;

import com.seed4j.generator.typescript.core.domain.TypescriptModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TypescriptApplicationService {

  private final TypescriptModuleFactory typescript;

  public TypescriptApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.typescript = new TypescriptModuleFactory(nodeLazyPackagesInstaller);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties project) {
    return typescript.buildModule(project);
  }
}
