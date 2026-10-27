package com.seed4j.generator.server.springboot.mvc.security.oauth2.core;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_MEMOIZERS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class OAuth2ModuleResource implements Seed4JModuleResourceProvider {

  private static final String AUTHENTICATION_TAG = "authentication";

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
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
      .factory(new OAuth2ModuleFactory(services)::buildModule);
  }
}
