package tech.jhipster.lite.generator.server.springboot.cucumber_authentication.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cucumber_authentication.application.CucumberAuthenticationApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CucumberAuthenticationModuleConfiguration {

  @Bean
  JHipsterModuleResource cucumberOAuth2AuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentications) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_OAUTH_2_AUTHENTICATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot - Component Tests", "Add OAuth2 authentication steps for cucumber")
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(CUCUMBER_AUTHENTICATION)
          .addDependency(SPRING_BOOT_CUCUMBER)
          .addDependency(SPRING_BOOT_OAUTH_2)
          .build()
      )
      .tags("server", "spring", "spring-boot", "test", "oauth2")
      .factory(cucumberAuthentications::buildOauth2Module);
  }

  @Bean
  JHipsterModuleResource cucumberJwtAuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentications) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_JWT_AUTHENTICATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - Component Tests", "Add JWT authentication steps for cucumber")
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(CUCUMBER_AUTHENTICATION)
          .addDependency(SPRING_BOOT_CUCUMBER)
          .addDependency(SPRING_BOOT_JWT)
          .build()
      )
      .tags("server", "spring", "spring-boot", "test", "jwt")
      .factory(cucumberAuthentications::buildJWTModule);
  }
}
