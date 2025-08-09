package com.seed4j.generator.server.springboot.mvc.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRINGDOC_JWT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_JWT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_JWT_BASIC_AUTH;

import com.seed4j.generator.server.springboot.mvc.security.jwt.application.JwtAuthenticationApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JwtAuthenticationModuleConfiguration {

  private static final String AUTHENTICATION_TAG = "authentication";

  @Bean
  SeedModuleResource jwtAuthenticationModule(JwtAuthenticationApplicationService jwtAuthentication) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_JWT)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - MVC - Security", "Add Spring Security JWT")
      .organization(
        SeedModuleOrganization.builder().feature(AUTHENTICATION).addDependency(JAVA_BASE).addDependency(SPRING_MVC_SERVER).build()
      )
      .tags("server", "spring", "spring-boot", AUTHENTICATION_TAG)
      .factory(jwtAuthentication::buildAuthenticationModule);
  }

  @Bean
  SeedModuleResource jwtBasicAuthModule(JwtAuthenticationApplicationService jwtAuthentication) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_JWT_BASIC_AUTH)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - MVC - Security", "Add Basic Auth for Spring Security JWT")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT_JWT).addDependency(SPRINGDOC_JWT).build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION_TAG)
      .factory(jwtAuthentication::buildBasicAuthModule);
  }
}
