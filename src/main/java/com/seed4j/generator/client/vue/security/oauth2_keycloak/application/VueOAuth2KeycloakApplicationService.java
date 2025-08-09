package com.seed4j.generator.client.vue.security.oauth2_keycloak.application;

import com.seed4j.generator.client.vue.security.oauth2_keycloak.domain.VueOAuth2KeycloakModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueOAuth2KeycloakApplicationService {

  private final VueOAuth2KeycloakModuleFactory vueOAuth2Keycloak;

  public VueOAuth2KeycloakApplicationService() {
    vueOAuth2Keycloak = new VueOAuth2KeycloakModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return vueOAuth2Keycloak.buildModule(properties);
  }
}
