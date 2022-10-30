package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtAuthenticationApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
public class JwtAuthenticationModulesConfiguration {

  private static final String AUTHENTICATION_TAG = "authentication";

  @Bean
  JHipsterModuleResource jwtAuthenticationModule(JwtAuthenticationApplicationService jwtAuthentications) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_JWT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Spring Boot - MVC - Security", "Add Spring Security JWT")
      .organization(
        JHipsterModuleOrganization.builder().feature(AUTHENTICATION).addDependency(JAVA_BASE).addDependency(SPRING_SERVER).build()
      )
      .tags("server", "spring", "spring-boot", AUTHENTICATION_TAG)
      .factory(jwtAuthentications::buildAuthenticationModule);
  }

  @Bean
  JHipsterModuleResource jwtBasicAuthModule(JwtAuthenticationApplicationService jwtAuthentications) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_JWT_BASIC_AUTH)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - MVC - Security", "Add Basic Auth for Spring Security JWT")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_JWT).addDependency(SPRINGDOC_JWT).build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION_TAG)
      .factory(jwtAuthentications::buildBasicAuthModule);
  }
}
