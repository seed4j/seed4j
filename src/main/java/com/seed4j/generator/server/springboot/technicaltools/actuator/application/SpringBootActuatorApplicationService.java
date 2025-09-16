package com.seed4j.generator.server.springboot.technicaltools.actuator.application;

import com.seed4j.generator.server.springboot.technicaltools.actuator.domain.SpringBootActuatorModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootActuatorApplicationService {

  private final SpringBootActuatorModuleFactory springBootActuator;

  public SpringBootActuatorApplicationService() {
    springBootActuator = new SpringBootActuatorModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springBootActuator.buildModule(properties);
  }
}
