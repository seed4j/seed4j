package com.seed4j.generator.client.tools.cypressmergecoverage;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CODE_COVERAGE_CLIENT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CYPRESS_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CYPRESS_MERGE_COVERAGE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class CypressMergeCoverageModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(CYPRESS_MERGE_COVERAGE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(
        "Frontend - Cypress merge coverage",
        "Merge coverage from unit test vitest and component test cypress. Not working with Angular"
      )
      .organization(Seed4JModuleOrganization.builder().feature(CODE_COVERAGE_CLIENT).addDependency(CYPRESS_COMPONENT_TESTS).build())
      .tags("client", "coverage", "cypress", "vitest")
      .factory(new CypressMergeCoverageModuleFactory()::buildCypressMergeCoverage);
  }
}
