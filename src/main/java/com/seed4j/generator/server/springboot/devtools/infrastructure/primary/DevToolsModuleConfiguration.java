package com.seed4j.generator.server.springboot.devtools.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_DEVTOOLS;

import com.seed4j.generator.server.springboot.devtools.application.DevToolsApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DevToolsModuleConfiguration {

  @Bean
  Seed4JModuleResource devToolsModule(DevToolsApplicationService devtools) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_DEVTOOLS)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Tools", "Add Spring Boot devtools.")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "devtools")
      .factory(devtools::buildModule);
  }
}
