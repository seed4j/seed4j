package tech.jhipster.lite.generator.client.hexagonal_documentation.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.hexagonal_documentation.domain.FrontHexagonalDocumentationModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class FrontHexagonalArchitectureDocumentationApplicationService {

  private static final FrontHexagonalDocumentationModuleFactory factory = new FrontHexagonalDocumentationModuleFactory();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
