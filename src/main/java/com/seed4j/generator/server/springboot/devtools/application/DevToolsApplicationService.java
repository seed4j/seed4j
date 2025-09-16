package com.seed4j.generator.server.springboot.devtools.application;

import com.seed4j.generator.server.springboot.devtools.domain.DevToolsModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class DevToolsApplicationService {

  private final DevToolsModuleFactory devTools;

  public DevToolsApplicationService() {
    devTools = new DevToolsModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return devTools.buildModule(properties);
  }
}
