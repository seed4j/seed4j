package com.seed4j.generator.server.springboot.logsspy.application;

import com.seed4j.generator.server.springboot.logsspy.domain.LogsSpyModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LogsSpyApplicationService {

  private final LogsSpyModuleFactory logsSpy;

  public LogsSpyApplicationService() {
    logsSpy = new LogsSpyModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return logsSpy.buildModule(properties);
  }
}
