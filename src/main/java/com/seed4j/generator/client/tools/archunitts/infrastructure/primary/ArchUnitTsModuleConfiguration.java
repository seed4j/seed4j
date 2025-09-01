package com.seed4j.generator.client.tools.archunitts.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.HEXAGONAL_ARCHITECTURE_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.ARCH_UNIT_TS;

import com.seed4j.generator.client.tools.archunitts.application.ArchUnitTsApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ArchUnitTsModuleConfiguration {

  @Bean
  SeedModuleResource archUnitTsModule(ArchUnitTsApplicationService archUnitTs) {
    return SeedModuleResource.builder()
      .slug(ARCH_UNIT_TS)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend", "Add Arch unit ts")
      .organization(SeedModuleOrganization.builder().feature(HEXAGONAL_ARCHITECTURE_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test")
      .factory(archUnitTs::buildModule);
  }
}
