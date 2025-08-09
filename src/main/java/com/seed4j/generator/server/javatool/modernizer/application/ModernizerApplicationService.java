package com.seed4j.generator.server.javatool.modernizer.application;

import com.seed4j.generator.server.javatool.modernizer.domain.ModernizerModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ModernizerApplicationService {

  private final ModernizerModuleFactory modernizer;

  public ModernizerApplicationService() {
    modernizer = new ModernizerModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return modernizer.buildModule(properties);
  }
}
