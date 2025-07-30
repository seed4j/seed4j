package com.seed4j.generator.client.tools.cypressmergecoverage.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CODE_COVERAGE_CLIENT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CYPRESS_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CYPRESS_MERGE_COVERAGE;

import com.seed4j.generator.client.tools.cypressmergecoverage.application.CypressMergeCoverageApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CypressMergeCoverageModuleConfiguration {

  @Bean
  JHipsterModuleResource cypressMergeCoverageModule(CypressMergeCoverageApplicationService cypressMergeCoverage) {
    return JHipsterModuleResource.builder()
      .slug(CYPRESS_MERGE_COVERAGE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(
        "Frontend - Cypress merge coverage",
        "Merge coverage from unit test vitest and component test cypress. Not working with Angular"
      )
      .organization(JHipsterModuleOrganization.builder().feature(CODE_COVERAGE_CLIENT).addDependency(CYPRESS_COMPONENT_TESTS).build())
      .tags("client", "coverage", "cypress", "vitest")
      .factory(cypressMergeCoverage::buildCypressMergeCoverage);
  }
}
