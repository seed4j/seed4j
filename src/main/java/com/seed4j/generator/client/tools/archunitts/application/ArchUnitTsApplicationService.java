package com.seed4j.generator.client.tools.archunitts.application;

import com.seed4j.generator.client.tools.archunitts.domain.ArchUnitTsModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ArchUnitTsApplicationService {

  private final ArchUnitTsModuleFactory archUnitTs = new ArchUnitTsModuleFactory();

  public SeedModule buildModule(SeedModuleProperties properties) {
    return archUnitTs.buildModule(properties);
  }
}
