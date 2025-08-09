package com.seed4j.generator.client.hexagonaldocumentation.application;

import com.seed4j.generator.client.hexagonaldocumentation.domain.FrontHexagonalDocumentationModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FrontHexagonalArchitectureDocumentationApplicationService {

  private static final FrontHexagonalDocumentationModuleFactory factory = new FrontHexagonalDocumentationModuleFactory();

  public SeedModule buildModule(SeedModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
