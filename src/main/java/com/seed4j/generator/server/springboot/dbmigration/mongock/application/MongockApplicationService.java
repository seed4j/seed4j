package com.seed4j.generator.server.springboot.dbmigration.mongock.application;

import com.seed4j.generator.server.springboot.dbmigration.mongock.domain.MongockModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MongockApplicationService {

  private final MongockModuleFactory mongock;

  public MongockApplicationService() {
    mongock = new MongockModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return mongock.buildModule(properties);
  }
}
