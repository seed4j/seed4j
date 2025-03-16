package tech.jhipster.lite.generator.client.vue.security.oauth2_keycloak.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.vue.security.oauth2_keycloak.domain.VueOAuth2KeycloakModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class VueOAuth2KeycloakApplicationService {

  private final VueOAuth2KeycloakModuleFactory vueOAuth2Keycloak;

  public VueOAuth2KeycloakApplicationService() {
    vueOAuth2Keycloak = new VueOAuth2KeycloakModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return vueOAuth2Keycloak.buildModule(properties);
  }
}
