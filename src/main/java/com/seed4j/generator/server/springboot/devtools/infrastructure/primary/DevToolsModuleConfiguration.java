package com.seed4j.generator.server.springboot.devtools.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_DEVTOOLS;

import com.seed4j.generator.server.springboot.devtools.application.DevToolsApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DevToolsModuleConfiguration {

  @Bean
  JHipsterModuleResource devToolsModule(DevToolsApplicationService devtools) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_DEVTOOLS)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Tools", "Add Spring Boot devtools.")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "devtools")
      .factory(devtools::buildModule);
  }
}
