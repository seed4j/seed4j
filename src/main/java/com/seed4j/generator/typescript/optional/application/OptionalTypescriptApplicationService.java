package com.seed4j.generator.typescript.optional.application;

import com.seed4j.generator.typescript.optional.domain.OptionalTypescriptModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OptionalTypescriptApplicationService {

  private final OptionalTypescriptModuleFactory optionalTypescript;

  public OptionalTypescriptApplicationService() {
    this.optionalTypescript = new OptionalTypescriptModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties project) {
    return optionalTypescript.buildModule(project);
  }
}
