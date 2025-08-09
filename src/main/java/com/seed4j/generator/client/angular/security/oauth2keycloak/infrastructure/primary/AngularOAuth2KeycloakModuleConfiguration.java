package com.seed4j.generator.client.angular.security.oauth2keycloak.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.ANGULAR_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.ANGULAR_OAUTH_2;

import com.seed4j.generator.client.angular.security.oauth2keycloak.application.AngularOAuth2KeycloakApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularOAuth2KeycloakModuleConfiguration {

  @Bean
  SeedModuleResource angularOAuth2Module(AngularOAuth2KeycloakApplicationService angularOAuth2) {
    return SeedModuleResource.builder()
      .slug(ANGULAR_OAUTH_2)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - Angular", "Add OAuth2 authentication")
      .organization(SeedModuleOrganization.builder().feature(ANGULAR_AUTHENTICATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular")
      .factory(angularOAuth2::buildModule);
  }
}
