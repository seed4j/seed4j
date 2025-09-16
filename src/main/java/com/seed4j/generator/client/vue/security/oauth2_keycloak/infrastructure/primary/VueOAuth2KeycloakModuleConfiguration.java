package com.seed4j.generator.client.vue.security.oauth2_keycloak.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.*;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.*;

import com.seed4j.generator.client.vue.security.oauth2_keycloak.application.VueOAuth2KeycloakApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueOAuth2KeycloakModuleConfiguration {

  @Bean
  Seed4JModuleResource vueOAuth2KeycloakModule(VueOAuth2KeycloakApplicationService vueOauth2Keycloak) {
    return Seed4JModuleResource.builder()
      .slug(VUE_OAUTH2_KEYCLOAK)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Vue", "Add OAuth2 Keycloak authentication to Vue")
      .organization(Seed4JModuleOrganization.builder().feature(VUE_AUTHENTICATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "auth", "oauth2", "keycloak")
      .factory(vueOauth2Keycloak::buildModule);
  }
}
