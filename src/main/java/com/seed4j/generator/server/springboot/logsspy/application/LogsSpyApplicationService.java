package com.seed4j.generator.server.springboot.logsspy.application;

import com.seed4j.generator.server.springboot.logsspy.domain.LogsSpyModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LogsSpyApplicationService {

  private final LogsSpyModuleFactory logsSpy;

  public LogsSpyApplicationService() {
    logsSpy = new LogsSpyModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return logsSpy.buildModule(properties);
  }
}
