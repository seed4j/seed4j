package com.seed4j.generator.server.springboot.mvc.security.oauth2.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_MEMOIZERS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.core.application.OAuth2ApplicationService;
import com.seed4j.generator.server.springboot.mvc.security.oauth2.core.domain.OAuth2ModuleFactory;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OAuth2ModuleConfiguration {

  private static final String AUTHENTICATION_TAG = "authentication";

  @Bean
  Seed4JModuleResource oAuth2Module(OAuth2ApplicationService oAuth2) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_OAUTH_2)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .add(
            Seed4JModulePropertyDefinition.optionalStringProperty(OAuth2ModuleFactory.REALM_NAME)
              .defaultValue(OAuth2ModuleFactory.DEFAULT_REALM_NAME)
              .description("Name of the realm used in Keycloak")
              .order(300)
              .build()
          )
          .add(
            Seed4JModulePropertyDefinition.optionalStringProperty(OAuth2ModuleFactory.CLIENT_SCOPE_NAME)
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
        Seed4JModuleOrganization.builder()
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
