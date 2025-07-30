package com.seed4j.generator.client.tools.playwright.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.E2E_TESTS;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.FRONTEND_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PLAYWRIGHT_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PLAYWRIGHT_E2E;

import com.seed4j.generator.client.tools.playwright.application.PlaywrightApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PlaywrightModuleConfiguration {

  @Bean
  JHipsterModuleResource playwrightComponentTestsModule(PlaywrightApplicationService playwright) {
    return JHipsterModuleResource.builder()
      .slug(PLAYWRIGHT_COMPONENT_TESTS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().build())
      .apiDoc("E2E", "Configure frontend component tests using Playwright")
      .organization(JHipsterModuleOrganization.builder().feature(FRONTEND_COMPONENT_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "playwright", "frontend")
      .factory(playwright::buildComponentTestsModule);
  }

  @Bean
  JHipsterModuleResource playwrightE2ETestsModule(PlaywrightApplicationService playwright) {
    return JHipsterModuleResource.builder()
      .slug(PLAYWRIGHT_E2E)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().build())
      .apiDoc("E2E", "Configure E2E tests using Playwright")
      .organization(JHipsterModuleOrganization.builder().feature(E2E_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test", "playwright", "e2e")
      .factory(playwright::buildE2ETestsModule);
  }
}
