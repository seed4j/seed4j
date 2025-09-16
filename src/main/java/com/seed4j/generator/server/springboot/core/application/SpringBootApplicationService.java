package com.seed4j.generator.server.springboot.core.application;

import com.seed4j.generator.server.springboot.core.domain.SpringBootCoreModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootApplicationService {

  private final SpringBootCoreModuleFactory springBootCore;

  public SpringBootApplicationService() {
    springBootCore = new SpringBootCoreModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springBootCore.buildModule(properties);
  }
}
