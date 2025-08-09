package com.seed4j.generator.server.javatool.archunit.application;

import com.seed4j.generator.server.javatool.archunit.domain.ArchUnitModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ArchUnitApplicationService {

  private final ArchUnitModuleFactory archUnit;

  public ArchUnitApplicationService() {
    archUnit = new ArchUnitModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return archUnit.buildModule(properties);
  }
}
