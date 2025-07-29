package com.seed4j.generator.server.springboot.mvc.security.oauth2.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.AUTHENTICATION;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JAVA_MEMOIZERS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_OAUTH_2;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.core.application.OAuth2ApplicationService;
import com.seed4j.generator.server.springboot.mvc.security.oauth2.core.domain.OAuth2ModuleFactory;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OAuth2ModuleConfiguration {

  private static final String AUTHENTICATION_TAG = "authentication";

  @Bean
  JHipsterModuleResource oAuth2Module(OAuth2ApplicationService oAuth2) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_OAUTH_2)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .add(
            JHipsterModulePropertyDefinition.optionalStringProperty(OAuth2ModuleFactory.REALM_NAME)
              .defaultValue(OAuth2ModuleFactory.DEFAULT_REALM_NAME)
              .description("Name of the realm used in Keycloak")
              .order(300)
              .build()
          )
          .add(
            JHipsterModulePropertyDefinition.optionalStringProperty(OAuth2ModuleFactory.CLIENT_SCOPE_NAME)
              .defaultValue(OAuth2ModuleFactory.DEFAULT_CLIENT_SCOPE_NAME)
              .description("Name of the client scope created in Keycloak")
              .order(300)
              .build()
          )
          .build()
      )
      .apiDoc(
        "Spring Boot - MVC - Security",
        "Add a Spring Security: OAuth 2.0 / OIDC Authentication (stateful, works with Keycloak and Okta)"
      )
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(AUTHENTICATION)
          .addDependency(JAVA_BASE)
          .addDependency(SPRING_MVC_SERVER)
          .addDependency(JAVA_MEMOIZERS)
          .build()
      )
      .tags("server", "spring", "spring-boot", AUTHENTICATION_TAG)
      .factory(oAuth2::buildModule);
  }
}
