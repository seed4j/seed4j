package com.seed4j.generator.server.javatool.modernizer.application;

import com.seed4j.generator.server.javatool.modernizer.domain.ModernizerModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ModernizerApplicationService {

  private final ModernizerModuleFactory modernizer;

  public ModernizerApplicationService() {
    modernizer = new ModernizerModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return modernizer.buildModule(properties);
  }
}
