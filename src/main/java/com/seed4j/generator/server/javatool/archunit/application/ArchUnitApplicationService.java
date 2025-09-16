package com.seed4j.generator.server.javatool.archunit.application;

import com.seed4j.generator.server.javatool.archunit.domain.ArchUnitModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ArchUnitApplicationService {

  private final ArchUnitModuleFactory archUnit;

  public ArchUnitApplicationService() {
    archUnit = new ArchUnitModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return archUnit.buildModule(properties);
  }
}
