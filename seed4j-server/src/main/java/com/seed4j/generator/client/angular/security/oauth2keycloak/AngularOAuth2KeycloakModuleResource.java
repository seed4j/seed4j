package com.seed4j.generator.client.angular.security.oauth2keycloak;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.ANGULAR_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_OAUTH_2;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class AngularOAuth2KeycloakModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_OAUTH_2)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addIndentation()
          .add(
            Seed4JModulePropertyDefinition.optionalStringProperty(AngularOAuth2KeycloakModuleFactory.REALM_NAME)
              .defaultValue(AngularOAuth2KeycloakModuleFactory.DEFAULT_REALM_NAME)
              .description("Name of the realm used in Keycloak")
              .order(300)
              .build()
          )
          .build()
      )
      .apiDoc("Frontend - Angular", "Add OAuth2 authentication")
      .organization(Seed4JModuleOrganization.builder().feature(ANGULAR_AUTHENTICATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular")
      .factory(new AngularOAuth2KeycloakModuleFactory()::buildModule);
  }
}
