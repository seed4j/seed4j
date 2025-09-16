package com.seed4j.generator.server.javatool.archunit.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_ARCHUNIT;

import com.seed4j.generator.server.javatool.archunit.application.ArchUnitApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ArchUnitModuleConfiguration {

  @Bean
  Seed4JModuleResource archUnitModule(ArchUnitApplicationService archUnit) {
    return Seed4JModuleResource.builder()
      .slug(JAVA_ARCHUNIT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add Hexagonal Arch Unit Tests to project")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "test")
      .factory(archUnit::buildModule);
  }
}
