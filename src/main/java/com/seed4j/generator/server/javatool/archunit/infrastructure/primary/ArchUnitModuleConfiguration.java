package com.seed4j.generator.server.javatool.archunit.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JAVA_ARCHUNIT;

import com.seed4j.generator.server.javatool.archunit.application.ArchUnitApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ArchUnitModuleConfiguration {

  @Bean
  SeedModuleResource archUnitModule(ArchUnitApplicationService archUnit) {
    return SeedModuleResource.builder()
      .slug(JAVA_ARCHUNIT)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add Hexagonal Arch Unit Tests to project")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "test")
      .factory(archUnit::buildModule);
  }
}
