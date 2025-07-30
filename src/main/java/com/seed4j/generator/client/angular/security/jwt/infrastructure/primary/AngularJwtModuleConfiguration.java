package com.seed4j.generator.client.angular.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.ANGULAR_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_JWT;

import com.seed4j.generator.client.angular.security.jwt.application.AngularJwtApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularJwtModuleConfiguration {

  @Bean
  JHipsterModuleResource angularJwtModule(AngularJwtApplicationService angularJwt) {
    return JHipsterModuleResource.builder()
      .slug(ANGULAR_JWT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Frontend - Angular", "Add Angular with authentication JWT")
      .organization(JHipsterModuleOrganization.builder().feature(ANGULAR_AUTHENTICATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular")
      .factory(angularJwt::buildModule);
  }
}
