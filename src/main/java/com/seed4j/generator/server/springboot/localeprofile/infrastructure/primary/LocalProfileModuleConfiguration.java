package com.seed4j.generator.server.springboot.localeprofile.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_LOCAL_PROFILE;

import com.seed4j.generator.server.springboot.localeprofile.application.LocalProfileApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LocalProfileModuleConfiguration {

  @Bean
  SeedModuleResource localProfile(LocalProfileApplicationService localProfile) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_LOCAL_PROFILE)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot", "Use Spring local profile by default for development.")
      .organization(SeedModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "dx")
      .factory(localProfile::buildModule);
  }
}
