package tech.jhipster.lite.generator.server.hexagonaldocumentation.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.hexagonaldocumentation.domain.HexagonalDocumentationModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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
