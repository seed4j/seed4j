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
class KipeExpressionModuleConfiguration {

  @Bean
  JHipsterModuleResource kipeExpressionModule(KipeApplicationService kipe) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.KIPE_EXPRESSION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - MVC - Security", "Create a new security expression for spring security: can('action', #element)")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteFeatureSlug.AUTHENTICATION).build())
      .tags("server", "spring", "spring-boot", "authentication")
      .factory(kipe::buildKipeExpressions);
  }
}
