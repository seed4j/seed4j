package com.seed4j.generator.server.hexagonaldocumentation.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.APPLICATION_SERVICE_HEXAGONAL_ARCHITECTURE_DOCUMENTATION;

import com.seed4j.generator.server.hexagonaldocumentation.application.HexagonalArchitectureDocumentationApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HexagonalArchitectureDocumentationModuleConfiguration {

  @Bean
  JHipsterModuleResource hexagonalArchitectureDocumentationModule(
    HexagonalArchitectureDocumentationApplicationService hexagonalArchitectureDocumentation
  ) {
    return JHipsterModuleResource.builder()
      .slug(APPLICATION_SERVICE_HEXAGONAL_ARCHITECTURE_DOCUMENTATION)
      .withoutProperties()
      .apiDoc("Documentation", "Add documentation for hexagonal architecture")
      .standalone()
      .tags("server", "documentation")
      .factory(hexagonalArchitectureDocumentation::buildModule);
  }
}
