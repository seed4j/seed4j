package com.seed4j.generator.client.vue.security.oauth2_keycloak.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.*;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.*;

import com.seed4j.generator.client.vue.security.oauth2_keycloak.application.VueOAuth2KeycloakApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueOAuth2KeycloakModuleConfiguration {

  @Bean
  JHipsterModuleResource vueOAuth2KeycloakModule(VueOAuth2KeycloakApplicationService vueOauth2Keycloak) {
    return JHipsterModuleResource.builder()
      .slug(VUE_OAUTH2_KEYCLOAK)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Vue", "Add OAuth2 Keycloak authentication to Vue")
      .organization(JHipsterModuleOrganization.builder().feature(VUE_AUTHENTICATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "auth", "oauth2", "keycloak")
      .factory(vueOauth2Keycloak::buildModule);
  }
}
