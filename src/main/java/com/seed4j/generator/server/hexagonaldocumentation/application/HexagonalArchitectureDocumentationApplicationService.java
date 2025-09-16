package com.seed4j.generator.server.hexagonaldocumentation.application;

import com.seed4j.generator.server.hexagonaldocumentation.domain.HexagonalDocumentationModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class HexagonalArchitectureDocumentationApplicationService {

  private final HexagonalDocumentationModuleFactory hexagonalDocumentation;

  public HexagonalArchitectureDocumentationApplicationService() {
    hexagonalDocumentation = new HexagonalDocumentationModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return hexagonalDocumentation.buildModule(properties);
  }
}
