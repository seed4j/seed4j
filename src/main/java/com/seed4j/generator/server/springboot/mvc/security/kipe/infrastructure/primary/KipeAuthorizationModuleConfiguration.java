package com.seed4j.generator.server.springboot.mvc.security.kipe.infrastructure.primary;

import com.seed4j.generator.server.springboot.mvc.security.kipe.application.KipeApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KipeAuthorizationModuleConfiguration {

  @Bean
  Seed4JModuleResource kipeAuthorizationModule(KipeApplicationService kipe) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.KIPE_AUTHORIZATION)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectName().build())
      .apiDoc("Spring Boot - MVC - Security", "Ease authorization matrices definition")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreFeatureSlug.AUTHENTICATION).build())
      .tags("server", "spring", "spring-boot", "authentication")
      .factory(kipe::buildKipeAuthorizations);
  }
}
