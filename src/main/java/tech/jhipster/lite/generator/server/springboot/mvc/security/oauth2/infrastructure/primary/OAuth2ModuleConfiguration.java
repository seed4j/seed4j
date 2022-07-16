package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class OAuth2ModuleConfiguration {

  @Bean
  JHipsterModuleResource oAuth2Module(OAuth2SecurityApplicationService oAuth2) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/security-systems/oauth2")
      .slug("springboot-oauth2")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc(
        new JHipsterModuleApiDoc(
          "Spring Boot - MVC - Security",
          "Add a Spring Security: OAuth 2.0 / OIDC Authentication (stateful, works with Keycloak and Okta)"
        )
      )
      .tags("server", "spring", "spring-boot", "authentication")
      .factory(oAuth2::buildOAuth2Module);
  }

  @Bean
  JHipsterModuleResource oAuth2AccountModule(OAuth2SecurityApplicationService oAuth2) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/security-systems/oauth2/account")
      .slug("springboot-oauth2-account")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - MVC - Security", "Add a account context for OAuth 2.0 / OIDC Authentication"))
      .tags("server", "spring", "spring-boot", "authentication", "account", "user")
      .factory(oAuth2::buildOAuth2AccountModule);
  }
}
