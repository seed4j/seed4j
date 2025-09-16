package com.seed4j.generator.client.hexagonaldocumentation.infrastructure.primary;

import com.seed4j.generator.client.hexagonaldocumentation.application.FrontHexagonalArchitectureDocumentationApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class FrontHexagonalDocumentationModuleConfiguration {

  @Bean
  Seed4JModuleResource frontHexagonalDocumentationModule(FrontHexagonalArchitectureDocumentationApplicationService documentation) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.FRONT_HEXAGONAL_ARCHITECTURE)
      .withoutProperties()
      .apiDoc("Frontend", "Add front hexagonal architecture documentation")
      .standalone()
      .tags("client", "documentation")
      .factory(documentation::buildModule);
  }
}
