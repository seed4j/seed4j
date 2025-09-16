package com.seed4j.generator.client.vue.security.oauth2_keycloak.application;

import com.seed4j.generator.client.vue.security.oauth2_keycloak.domain.VueOAuth2KeycloakModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueOAuth2KeycloakApplicationService {

  private final VueOAuth2KeycloakModuleFactory vueOAuth2Keycloak;

  public VueOAuth2KeycloakApplicationService() {
    vueOAuth2Keycloak = new VueOAuth2KeycloakModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return vueOAuth2Keycloak.buildModule(properties);
  }
}
