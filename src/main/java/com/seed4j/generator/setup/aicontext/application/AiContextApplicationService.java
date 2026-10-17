package com.seed4j.generator.setup.aicontext.application;

import com.seed4j.generator.setup.aicontext.domain.AiContextModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class AiContextApplicationService {

  private final AiContextModuleFactory aiContext;

  public AiContextApplicationService() {
    aiContext = new AiContextModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return aiContext.buildModule(properties);
  }
}
