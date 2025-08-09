package com.seed4j.generator.server.springboot.technicaltools.actuator.application;

import com.seed4j.generator.server.springboot.technicaltools.actuator.domain.SpringBootActuatorModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootActuatorApplicationService {

  private final SpringBootActuatorModuleFactory springBootActuator;

  public SpringBootActuatorApplicationService() {
    springBootActuator = new SpringBootActuatorModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return springBootActuator.buildModule(properties);
  }
}
