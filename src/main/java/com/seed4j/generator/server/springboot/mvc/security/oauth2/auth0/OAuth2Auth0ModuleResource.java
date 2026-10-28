package com.seed4j.generator.server.springboot.mvc.security.oauth2.auth0;

import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.mandatoryStringProperty;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.OAUTH_PROVIDER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2_AUTH_0;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class OAuth2Auth0ModuleResource implements Seed4JModuleResourceProvider {

  private static final String AUTHENTICATION = "authentication";

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_OAUTH_2_AUTH_0)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc(
        "Spring Boot - MVC - Security",
        "Add a Spring Security: OAuth 2.0 / OIDC Authentication / Auth0 Provider (stateful, works with Keycloak and Auth0)"
      )
      .organization(Seed4JModuleOrganization.builder().feature(OAUTH_PROVIDER).addDependency(SPRING_BOOT_OAUTH_2).build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION, "auth0")
      .factory(new OAuth2Auth0ModuleFactory()::buildModule);
  }

  private static Seed4JModulePropertiesDefinition initPropertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder()
      .addProjectBaseName()
      .addProjectName()
      .add(auth0Domain())
      .add(auth0clientId())
      .addIndentation()
      .addSpringConfigurationFormat()
      .build();
  }

  public static Seed4JModulePropertyDefinition auth0Domain() {
    return mandatoryStringProperty("auth0Domain").description("Auth0 domain").defaultValue("dev-123456.us.auth0.com").order(800).build();
  }

  public static Seed4JModulePropertyDefinition auth0clientId() {
    return mandatoryStringProperty("auth0ClientId")
      .description("Auth0 Client ID for OIDC application")
      .defaultValue("0oab8eb55Kb9jdMIr5d6")
      .order(900)
      .build();
  }
}
