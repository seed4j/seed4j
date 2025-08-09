package com.seed4j.generator.client.tools.playwright.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.E2E_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.FRONTEND_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PLAYWRIGHT_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PLAYWRIGHT_E2E;

import com.seed4j.generator.client.tools.playwright.application.PlaywrightApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PlaywrightModuleConfiguration {

  @Bean
  SeedModuleResource playwrightComponentTestsModule(PlaywrightApplicationService playwright) {
    return SeedModuleResource.builder()
      .slug(PLAYWRIGHT_COMPONENT_TESTS)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().build())
      .apiDoc("E2E", "Configure frontend component tests using Playwright")
      .organization(SeedModuleOrganization.builder().feature(FRONTEND_COMPONENT_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "playwright", "frontend")
      .factory(playwright::buildComponentTestsModule);
  }

  @Bean
  SeedModuleResource playwrightE2ETestsModule(PlaywrightApplicationService playwright) {
    return SeedModuleResource.builder()
      .slug(PLAYWRIGHT_E2E)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().build())
      .apiDoc("E2E", "Configure E2E tests using Playwright")
      .organization(SeedModuleOrganization.builder().feature(E2E_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "playwright", "e2e")
      .factory(playwright::buildE2ETestsModule);
  }
}
