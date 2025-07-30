package com.seed4j.generator.server.javatool.modernizer.application;

import com.seed4j.generator.server.javatool.modernizer.domain.ModernizerModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ModernizerApplicationService {

  private final ModernizerModuleFactory modernizer;

  public ModernizerApplicationService() {
    modernizer = new ModernizerModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return modernizer.buildModule(properties);
  }
}
