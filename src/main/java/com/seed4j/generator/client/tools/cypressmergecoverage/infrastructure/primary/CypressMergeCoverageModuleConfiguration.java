package com.seed4j.generator.client.tools.cypressmergecoverage.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CODE_COVERAGE_CLIENT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CYPRESS_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CYPRESS_MERGE_COVERAGE;

import com.seed4j.generator.client.tools.cypressmergecoverage.application.CypressMergeCoverageApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CypressMergeCoverageModuleConfiguration {

  @Bean
  Seed4JModuleResource cypressMergeCoverageModule(CypressMergeCoverageApplicationService cypressMergeCoverage) {
    return Seed4JModuleResource.builder()
      .slug(CYPRESS_MERGE_COVERAGE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(
        "Frontend - Cypress merge coverage",
        "Merge coverage from unit test vitest and component test cypress. Not working with Angular"
      )
      .organization(Seed4JModuleOrganization.builder().feature(CODE_COVERAGE_CLIENT).addDependency(CYPRESS_COMPONENT_TESTS).build())
      .tags("client", "coverage", "cypress", "vitest")
      .factory(cypressMergeCoverage::buildCypressMergeCoverage);
  }
}
