package com.seed4j.generator.server.springboot.core.application;

import com.seed4j.generator.server.springboot.core.domain.SpringBootCoreModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootApplicationService {

  private final SpringBootCoreModuleFactory springBootCore;

  public SpringBootApplicationService() {
    springBootCore = new SpringBootCoreModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return springBootCore.buildModule(properties);
  }
}
