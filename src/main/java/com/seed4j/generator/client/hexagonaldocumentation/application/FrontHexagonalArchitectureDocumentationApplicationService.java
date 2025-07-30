package com.seed4j.generator.client.hexagonaldocumentation.application;

import com.seed4j.generator.client.hexagonaldocumentation.domain.FrontHexagonalDocumentationModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FrontHexagonalArchitectureDocumentationApplicationService {

  private static final FrontHexagonalDocumentationModuleFactory factory = new FrontHexagonalDocumentationModuleFactory();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
