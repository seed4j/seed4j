package com.seed4j.generator.client.angular.security.oauth2keycloak.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.ANGULAR_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_OAUTH_2;

import com.seed4j.generator.client.angular.security.oauth2keycloak.application.AngularOAuth2KeycloakApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularOAuth2KeycloakModuleConfiguration {

  @Bean
  JHipsterModuleResource angularOAuth2Module(AngularOAuth2KeycloakApplicationService angularOAuth2) {
    return JHipsterModuleResource.builder()
      .slug(ANGULAR_OAUTH_2)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - Angular", "Add OAuth2 authentication")
      .organization(JHipsterModuleOrganization.builder().feature(ANGULAR_AUTHENTICATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular")
      .factory(angularOAuth2::buildModule);
  }
}
