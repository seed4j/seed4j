package com.seed4j.generator.init.application;

import com.seed4j.generator.init.domain.InitModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class InitApplicationService {

  private final InitModuleFactory init;

  public InitApplicationService(NodeVersions nodeVersions) {
    init = new InitModuleFactory(nodeVersions);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return init.buildModule(properties);
  }
}
