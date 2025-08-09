package com.seed4j.generator.server.springboot.devtools.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_DEVTOOLS;

import com.seed4j.generator.server.springboot.devtools.application.DevToolsApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DevToolsModuleConfiguration {

  @Bean
  SeedModuleResource devToolsModule(DevToolsApplicationService devtools) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_DEVTOOLS)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Tools", "Add Spring Boot devtools.")
      .organization(SeedModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "devtools")
      .factory(devtools::buildModule);
  }
}
