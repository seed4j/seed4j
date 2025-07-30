package com.seed4j.generator.server.springboot.logging.logstash.application;

import com.seed4j.generator.server.springboot.logging.logstash.domain.LogstashModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LogstashApplicationService {

  private final LogstashModuleFactory logstash;

  public LogstashApplicationService() {
    logstash = new LogstashModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return logstash.buildModule(properties);
  }
}
