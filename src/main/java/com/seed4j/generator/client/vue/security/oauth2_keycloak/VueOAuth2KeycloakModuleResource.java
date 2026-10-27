package com.seed4j.generator.client.vue.security.oauth2_keycloak;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.*;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.*;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class VueOAuth2KeycloakModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(VUE_OAUTH2_KEYCLOAK)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addIndentation()
          .add(
            Seed4JModulePropertyDefinition.optionalStringProperty(VueOAuth2KeycloakModuleFactory.REALM_NAME)
              .defaultValue(VueOAuth2KeycloakModuleFactory.DEFAULT_REALM_NAME)
              .description("Name of the realm used in Keycloak")
              .order(300)
              .build()
          )
          .build()
      )
      .apiDoc("Vue", "Add OAuth2 Keycloak authentication to Vue")
      .organization(Seed4JModuleOrganization.builder().feature(VUE_AUTHENTICATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "auth", "oauth2", "keycloak")
      .factory(new VueOAuth2KeycloakModuleFactory()::buildModule);
  }
}
