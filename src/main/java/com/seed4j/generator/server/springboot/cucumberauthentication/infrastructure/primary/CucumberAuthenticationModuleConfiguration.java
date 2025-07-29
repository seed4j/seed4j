package com.seed4j.generator.server.springboot.cucumberauthentication.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CUCUMBER_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_BOOT_CUCUMBER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_JWT_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_OAUTH_2_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_JWT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_OAUTH_2;

import com.seed4j.generator.server.springboot.cucumberauthentication.application.CucumberAuthenticationApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CucumberAuthenticationModuleConfiguration {

  @Bean
  JHipsterModuleResource cucumberOAuth2AuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentication) {
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
      .factory(cucumberAuthentication::buildOauth2Module);
  }

  @Bean
  JHipsterModuleResource cucumberJwtAuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentication) {
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
      .factory(cucumberAuthentication::buildJWTModule);
  }
}
