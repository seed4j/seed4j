package com.seed4j.generator.server.javatool.archunit.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JAVA_ARCHUNIT;

import com.seed4j.generator.server.javatool.archunit.application.ArchUnitApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ArchUnitModuleConfiguration {

  @Bean
  JHipsterModuleResource archUnitModule(ArchUnitApplicationService archUnit) {
    return JHipsterModuleResource.builder()
      .slug(JAVA_ARCHUNIT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add Hexagonal Arch Unit Tests to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "test")
      .factory(archUnit::buildModule);
  }
}
