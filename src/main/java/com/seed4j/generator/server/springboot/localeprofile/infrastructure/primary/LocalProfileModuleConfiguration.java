package com.seed4j.generator.server.springboot.localeprofile.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_LOCAL_PROFILE;

import com.seed4j.generator.server.springboot.localeprofile.application.LocalProfileApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LocalProfileModuleConfiguration {

  @Bean
  JHipsterModuleResource localProfile(LocalProfileApplicationService localProfile) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_LOCAL_PROFILE)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot", "Use Spring local profile by default for development.")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "dx")
      .factory(localProfile::buildModule);
  }
}
