package com.seed4j.generator.server.hexagonaldocumentation.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.APPLICATION_SERVICE_HEXAGONAL_ARCHITECTURE_DOCUMENTATION;

import com.seed4j.generator.server.hexagonaldocumentation.application.HexagonalArchitectureDocumentationApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HexagonalArchitectureDocumentationModuleConfiguration {

  @Bean
  Seed4JModuleResource hexagonalArchitectureDocumentationModule(
    HexagonalArchitectureDocumentationApplicationService hexagonalArchitectureDocumentation
  ) {
    return Seed4JModuleResource.builder()
      .slug(APPLICATION_SERVICE_HEXAGONAL_ARCHITECTURE_DOCUMENTATION)
      .withoutProperties()
      .apiDoc("Documentation", "Add documentation for hexagonal architecture")
      .standalone()
      .tags("server", "documentation")
      .factory(hexagonalArchitectureDocumentation::buildModule);
  }
}
