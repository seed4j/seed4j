package com.seed4j.generator.server.springboot.dbmigration.mongock.application;

import com.seed4j.generator.server.springboot.dbmigration.mongock.domain.MongockModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MongockApplicationService {

  private final MongockModuleFactory mongock;

  public MongockApplicationService() {
    mongock = new MongockModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return mongock.buildModule(properties);
  }
}
