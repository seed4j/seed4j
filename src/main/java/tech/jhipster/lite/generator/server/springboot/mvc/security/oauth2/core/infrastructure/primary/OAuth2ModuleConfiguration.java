package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.core.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.core.application.OAuth2SecurityApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class OAuth2ModuleConfiguration {

  private static final String AUTHENTICATION_TAG = "authentication";

  @Bean
  JHipsterModuleResource oAuth2Module(OAuth2SecurityApplicationService oAuth2) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_OAUTH_2)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc(
        "Spring Boot - MVC - Security",
        "Add a Spring Security: OAuth 2.0 / OIDC Authentication (stateful, works with Keycloak and Okta)"
      )
      .organization(
        JHipsterModuleOrganization.builder().feature(AUTHENTICATION).addDependency(JAVA_BASE).addDependency(SPRING_SERVER).build()
      )
      .tags("server", "spring", "spring-boot", AUTHENTICATION_TAG)
      .factory(oAuth2::buildOAuth2Module);
  }
}
