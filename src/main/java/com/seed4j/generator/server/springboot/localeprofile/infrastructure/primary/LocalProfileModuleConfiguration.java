package com.seed4j.generator.server.springboot.localeprofile.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_LOCAL_PROFILE;

import com.seed4j.generator.server.springboot.localeprofile.application.LocalProfileApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LocalProfileModuleConfiguration {

  @Bean
  Seed4JModuleResource localProfile(LocalProfileApplicationService localProfile) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_LOCAL_PROFILE)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot", "Use Spring local profile by default for development.")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "dx")
      .factory(localProfile::buildModule);
  }
}
