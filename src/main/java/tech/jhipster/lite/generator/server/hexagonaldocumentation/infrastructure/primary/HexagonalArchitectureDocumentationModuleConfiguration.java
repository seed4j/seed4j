package tech.jhipster.lite.generator.server.hexagonaldocumentation.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.hexagonaldocumentation.application.HexagonalArchitectureDocumentationApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class HexagonalArchitectureDocumentationModuleConfiguration {

  @Bean
  JHipsterModuleResource hexagonalArchitectureDocumentationModule(
    HexagonalArchitectureDocumentationApplicationService hexagonalArchitectureDocumentations
  ) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/hexagonal-architecture-documentation")
      .slug("application-service-hexagonal-architecture-documentation")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot", "Add documentation for hexagonal architecture"))
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("git-init").build())
      .tags("server", "documentation")
      .factory(hexagonalArchitectureDocumentations::buildModule);
  }
}
