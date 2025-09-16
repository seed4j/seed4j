package com.seed4j.generator.client.angular.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.ANGULAR_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_JWT;

import com.seed4j.generator.client.angular.security.jwt.application.AngularJwtApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularJwtModuleConfiguration {

  @Bean
  Seed4JModuleResource angularJwtModule(AngularJwtApplicationService angularJwt) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_JWT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Frontend - Angular", "Add Angular with authentication JWT")
      .organization(Seed4JModuleOrganization.builder().feature(ANGULAR_AUTHENTICATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular")
      .factory(angularJwt::buildModule);
  }
}
