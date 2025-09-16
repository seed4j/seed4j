package com.seed4j.generator.client.tools.cypress.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.E2E_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.FRONTEND_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CYPRESS_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CYPRESS_E2E;

import com.seed4j.generator.client.tools.cypress.application.CypressApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CypressModuleConfiguration {

  @Bean
  Seed4JModuleResource cypressComponentTestsModule(CypressApplicationService cypress) {
    return Seed4JModuleResource.builder()
      .slug(CYPRESS_COMPONENT_TESTS)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - Component tests", "Setup frontend component tests using Cypress")
      .organization(Seed4JModuleOrganization.builder().feature(FRONTEND_COMPONENT_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "cypress", "frontend")
      .factory(cypress::buildComponentTestsModule);
  }

  @Bean
  Seed4JModuleResource cypressE2ETestsModule(CypressApplicationService cypress) {
    return Seed4JModuleResource.builder()
      .slug(CYPRESS_E2E)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("E2E", "Setup E2E tests using Cypress")
      .organization(Seed4JModuleOrganization.builder().feature(E2E_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "cypress", "e2e")
      .factory(cypress::buildE2ETestsModule);
  }
}
