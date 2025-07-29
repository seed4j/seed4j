package com.seed4j.generator.server.springboot.devtools.application;

import com.seed4j.generator.server.springboot.devtools.domain.DevToolsModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class DevToolsApplicationService {

  private final DevToolsModuleFactory devTools;

  public DevToolsApplicationService() {
    devTools = new DevToolsModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return devTools.buildModule(properties);
  }
}
