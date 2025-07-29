package com.seed4j.generator.server.hexagonaldocumentation.application;

import com.seed4j.generator.server.hexagonaldocumentation.domain.HexagonalDocumentationModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class HexagonalArchitectureDocumentationApplicationService {

  private final HexagonalDocumentationModuleFactory hexagonalDocumentation;

  public HexagonalArchitectureDocumentationApplicationService() {
    hexagonalDocumentation = new HexagonalDocumentationModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return hexagonalDocumentation.buildModule(properties);
  }
}
