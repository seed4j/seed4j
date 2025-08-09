package com.seed4j.generator.server.springboot.mvc.security.kipe.infrastructure.primary;

import com.seed4j.generator.server.springboot.mvc.security.kipe.application.KipeApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.Seed4JFeatureSlug;
import com.seed4j.shared.slug.domain.Seed4JModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KipeAuthorizationModuleConfiguration {

  @Bean
  SeedModuleResource kipeAuthorizationModule(KipeApplicationService kipe) {
    return SeedModuleResource.builder()
      .slug(Seed4JModuleSlug.KIPE_AUTHORIZATION)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectName().build())
      .apiDoc("Spring Boot - MVC - Security", "Ease authorization matrices definition")
      .organization(SeedModuleOrganization.builder().addDependency(Seed4JFeatureSlug.AUTHENTICATION).build())
      .tags("server", "spring", "spring-boot", "authentication")
      .factory(kipe::buildKipeAuthorizations);
  }
}
