package com.seed4j.generator.server.springboot.seed4jextension.application;

import com.seed4j.generator.server.springboot.seed4jextension.domain.Seed4JExtensionModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class Seed4JExtensionApplicationService {

  private final Seed4JExtensionModuleFactory factory;

  public Seed4JExtensionApplicationService() {
    factory = new Seed4JExtensionModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
