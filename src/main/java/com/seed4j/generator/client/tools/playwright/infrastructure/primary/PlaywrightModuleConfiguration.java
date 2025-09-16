package com.seed4j.generator.client.tools.playwright.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.E2E_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.FRONTEND_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PLAYWRIGHT_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PLAYWRIGHT_E2E;

import com.seed4j.generator.client.tools.playwright.application.PlaywrightApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PlaywrightModuleConfiguration {

  @Bean
  Seed4JModuleResource playwrightComponentTestsModule(PlaywrightApplicationService playwright) {
    return Seed4JModuleResource.builder()
      .slug(PLAYWRIGHT_COMPONENT_TESTS)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("E2E", "Configure frontend component tests using Playwright")
      .organization(Seed4JModuleOrganization.builder().feature(FRONTEND_COMPONENT_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "playwright", "frontend")
      .factory(playwright::buildComponentTestsModule);
  }

  @Bean
  Seed4JModuleResource playwrightE2ETestsModule(PlaywrightApplicationService playwright) {
    return Seed4JModuleResource.builder()
      .slug(PLAYWRIGHT_E2E)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("E2E", "Configure E2E tests using Playwright")
      .organization(Seed4JModuleOrganization.builder().feature(E2E_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "playwright", "e2e")
      .factory(playwright::buildE2ETestsModule);
  }
}
