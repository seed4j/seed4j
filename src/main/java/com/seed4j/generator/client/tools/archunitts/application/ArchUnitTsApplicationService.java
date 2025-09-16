package com.seed4j.generator.client.tools.archunitts.application;

import com.seed4j.generator.client.tools.archunitts.domain.ArchUnitTsModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ArchUnitTsApplicationService {

  private final ArchUnitTsModuleFactory archUnitTs = new ArchUnitTsModuleFactory();

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return archUnitTs.buildModule(properties);
  }
}
