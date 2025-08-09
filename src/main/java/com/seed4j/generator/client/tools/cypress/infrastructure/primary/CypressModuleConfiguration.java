package com.seed4j.generator.client.tools.cypress.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.E2E_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.FRONTEND_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.CYPRESS_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.CYPRESS_E2E;

import com.seed4j.generator.client.tools.cypress.application.CypressApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CypressModuleConfiguration {

  @Bean
  SeedModuleResource cypressComponentTestsModule(CypressApplicationService cypress) {
    return SeedModuleResource.builder()
      .slug(CYPRESS_COMPONENT_TESTS)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - Component tests", "Setup frontend component tests using Cypress")
      .organization(SeedModuleOrganization.builder().feature(FRONTEND_COMPONENT_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "cypress", "frontend")
      .factory(cypress::buildComponentTestsModule);
  }

  @Bean
  SeedModuleResource cypressE2ETestsModule(CypressApplicationService cypress) {
    return SeedModuleResource.builder()
      .slug(CYPRESS_E2E)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("E2E", "Setup E2E tests using Cypress")
      .organization(SeedModuleOrganization.builder().feature(E2E_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "cypress", "e2e")
      .factory(cypress::buildE2ETestsModule);
  }
}
