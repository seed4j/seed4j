package com.seed4j.generator.server.springboot.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.springboot.core.application.SpringBootApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootCoreModuleConfiguration {

  @Bean
  SeedModuleResource springBootCoreModule(SpringBootApplicationService springBoot) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot", "Init Spring Boot project with dependencies, App, and properties")
      .organization(SeedModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(JAVA_BASE).build())
      .tags("server", "spring", "spring-boot")
      .factory(springBoot::buildModule);
  }
}
