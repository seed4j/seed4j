package com.seed4j.generator.server.springboot.mvc.security.oauth2.auth0.infrastructure.primary;

import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.mandatoryStringProperty;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.OAUTH_PROVIDER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_OAUTH_2;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_OAUTH_2_AUTH_0;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.auth0.application.OAuth2Auth0ApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OAuth2Auth0ModuleConfiguration {

  private static final String AUTHENTICATION = "authentication";

  @Bean
  JHipsterModuleResource oAuth2Auth0Module(OAuth2Auth0ApplicationService oAuth2) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_OAUTH_2_AUTH_0)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc(
        "Spring Boot - MVC - Security",
        "Add a Spring Security: OAuth 2.0 / OIDC Authentication / Auth0 Provider (stateful, works with Keycloak and Auth0)"
      )
      .organization(JHipsterModuleOrganization.builder().feature(OAUTH_PROVIDER).addDependency(SPRING_BOOT_OAUTH_2).build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION, "auth0")
      .factory(oAuth2::buildModule);
  }

  private JHipsterModulePropertiesDefinition initPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder()
      .addProjectBaseName()
      .addProjectName()
      .add(auth0Domain())
      .add(auth0clientId())
      .addIndentation()
      .addSpringConfigurationFormat()
      .build();
  }

  public static JHipsterModulePropertyDefinition auth0Domain() {
    return mandatoryStringProperty("auth0Domain").description("Auth0 domain").defaultValue("dev-123456.us.auth0.com").order(800).build();
  }

  public static JHipsterModulePropertyDefinition auth0clientId() {
    return mandatoryStringProperty("auth0ClientId")
      .description("Auth0 Client ID for OIDC application")
      .defaultValue("0oab8eb55Kb9jdMIr5d6")
      .order(900)
      .build();
  }
}
