package com.seed4j.generator.client.hexagonaldocumentation.infrastructure.primary;

import com.seed4j.generator.client.hexagonaldocumentation.application.FrontHexagonalArchitectureDocumentationApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class FrontHexagonalDocumentationModuleConfiguration {

  @Bean
  JHipsterModuleResource frontHexagonalDocumentationModule(FrontHexagonalArchitectureDocumentationApplicationService documentation) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.FRONT_HEXAGONAL_ARCHITECTURE)
      .withoutProperties()
      .apiDoc("Frontend", "Add front hexagonal architecture documentation")
      .standalone()
      .tags("client", "documentation")
      .factory(documentation::buildModule);
  }
}
