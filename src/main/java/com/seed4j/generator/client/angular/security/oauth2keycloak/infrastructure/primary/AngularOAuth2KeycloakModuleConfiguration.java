package com.seed4j.generator.client.angular.security.oauth2keycloak.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.ANGULAR_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_OAUTH_2;

import com.seed4j.generator.client.angular.security.oauth2keycloak.application.AngularOAuth2KeycloakApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularOAuth2KeycloakModuleConfiguration {

  @Bean
  Seed4JModuleResource angularOAuth2Module(AngularOAuth2KeycloakApplicationService angularOAuth2) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_OAUTH_2)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - Angular", "Add OAuth2 authentication")
      .organization(Seed4JModuleOrganization.builder().feature(ANGULAR_AUTHENTICATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular")
      .factory(angularOAuth2::buildModule);
  }
}
