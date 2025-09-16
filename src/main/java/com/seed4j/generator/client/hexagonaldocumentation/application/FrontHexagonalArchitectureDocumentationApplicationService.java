package com.seed4j.generator.client.hexagonaldocumentation.application;

import com.seed4j.generator.client.hexagonaldocumentation.domain.FrontHexagonalDocumentationModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FrontHexagonalArchitectureDocumentationApplicationService {

  private static final FrontHexagonalDocumentationModuleFactory factory = new FrontHexagonalDocumentationModuleFactory();

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
