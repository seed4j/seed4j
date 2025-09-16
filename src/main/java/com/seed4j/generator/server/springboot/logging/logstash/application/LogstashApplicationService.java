package com.seed4j.generator.server.springboot.logging.logstash.application;

import com.seed4j.generator.server.springboot.logging.logstash.domain.LogstashModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LogstashApplicationService {

  private final LogstashModuleFactory logstash;

  public LogstashApplicationService() {
    logstash = new LogstashModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return logstash.buildModule(properties);
  }
}
