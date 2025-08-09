package com.seed4j.generator.client.tools.cypressmergecoverage.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CODE_COVERAGE_CLIENT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CYPRESS_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CYPRESS_MERGE_COVERAGE;

import com.seed4j.generator.client.tools.cypressmergecoverage.application.CypressMergeCoverageApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CypressMergeCoverageModuleConfiguration {

  @Bean
  SeedModuleResource cypressMergeCoverageModule(CypressMergeCoverageApplicationService cypressMergeCoverage) {
    return SeedModuleResource.builder()
      .slug(CYPRESS_MERGE_COVERAGE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(
        "Frontend - Cypress merge coverage",
        "Merge coverage from unit test vitest and component test cypress. Not working with Angular"
      )
      .organization(SeedModuleOrganization.builder().feature(CODE_COVERAGE_CLIENT).addDependency(CYPRESS_COMPONENT_TESTS).build())
      .tags("client", "coverage", "cypress", "vitest")
      .factory(cypressMergeCoverage::buildCypressMergeCoverage);
  }
}
