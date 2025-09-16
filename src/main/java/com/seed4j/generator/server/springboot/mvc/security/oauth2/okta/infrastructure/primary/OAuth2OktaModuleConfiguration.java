package com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.infrastructure.primary;

import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.mandatoryStringProperty;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.OAUTH_PROVIDER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2_OKTA;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.application.OAuth2OktaApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OAuth2OktaModuleConfiguration {

  private static final String AUTHENTICATION = "authentication";

  @Bean
  Seed4JModuleResource oAuth2OktaModule(OAuth2OktaApplicationService oAuth2) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_OAUTH_2_OKTA)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc(
        "Spring Boot - MVC - Security",
        "Add a Spring Security: OAuth 2.0 / OIDC Authentication / Okta Provider (stateful, works with Keycloak and Okta)"
      )
      .organization(Seed4JModuleOrganization.builder().feature(OAUTH_PROVIDER).addDependency(SPRING_BOOT_OAUTH_2).build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION, "okta")
      .factory(oAuth2::buildModule);
  }

  private Seed4JModulePropertiesDefinition initPropertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder()
      .addProjectBaseName()
      .addProjectName()
      .add(oktaDomain())
      .add(oktaClientId())
      .addIndentation()
      .addSpringConfigurationFormat()
      .build();
  }

  public static Seed4JModulePropertyDefinition oktaDomain() {
    return mandatoryStringProperty("oktaDomain").description("Okta domain").defaultValue("dev-123456.okta.com").order(600).build();
  }

  public static Seed4JModulePropertyDefinition oktaClientId() {
    return mandatoryStringProperty("oktaClientId")
      .description("Okta Client ID for OIDC application")
      .defaultValue("0oab8eb55Kb9jdMIr5d6")
      .order(700)
      .build();
  }
}
