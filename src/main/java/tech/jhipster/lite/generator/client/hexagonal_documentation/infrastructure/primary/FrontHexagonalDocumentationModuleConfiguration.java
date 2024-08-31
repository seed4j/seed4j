package tech.jhipster.lite.generator.client.hexagonal_documentation.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.client.hexagonal_documentation.application.FrontHexagonalArchitectureDocumentationApplicationService;
import tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

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
