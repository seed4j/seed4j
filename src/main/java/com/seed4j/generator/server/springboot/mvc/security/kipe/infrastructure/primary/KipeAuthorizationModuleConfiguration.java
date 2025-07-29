package com.seed4j.generator.server.springboot.mvc.security.kipe.infrastructure.primary;

import com.seed4j.generator.server.springboot.mvc.security.kipe.application.KipeApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteFeatureSlug;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KipeAuthorizationModuleConfiguration {

  @Bean
  JHipsterModuleResource kipeAuthorizationModule(KipeApplicationService kipe) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.KIPE_AUTHORIZATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectName().build())
      .apiDoc("Spring Boot - MVC - Security", "Ease authorization matrices definition")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteFeatureSlug.AUTHENTICATION).build())
      .tags("server", "spring", "spring-boot", "authentication")
      .factory(kipe::buildKipeAuthorizations);
  }
}
