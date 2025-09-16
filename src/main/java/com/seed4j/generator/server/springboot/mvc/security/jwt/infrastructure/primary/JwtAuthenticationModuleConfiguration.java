package com.seed4j.generator.server.springboot.mvc.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_JWT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_JWT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_JWT_BASIC_AUTH;

import com.seed4j.generator.server.springboot.mvc.security.jwt.application.JwtAuthenticationApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JwtAuthenticationModuleConfiguration {

  private static final String AUTHENTICATION_TAG = "authentication";

  @Bean
  Seed4JModuleResource jwtAuthenticationModule(JwtAuthenticationApplicationService jwtAuthentication) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_JWT)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - MVC - Security", "Add Spring Security JWT")
      .organization(
        Seed4JModuleOrganization.builder().feature(AUTHENTICATION).addDependency(JAVA_BASE).addDependency(SPRING_MVC_SERVER).build()
      )
      .tags("server", "spring", "spring-boot", AUTHENTICATION_TAG)
      .factory(jwtAuthentication::buildAuthenticationModule);
  }

  @Bean
  Seed4JModuleResource jwtBasicAuthModule(JwtAuthenticationApplicationService jwtAuthentication) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_JWT_BASIC_AUTH)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - MVC - Security", "Add Basic Auth for Spring Security JWT")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_JWT).addDependency(SPRINGDOC_JWT).build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION_TAG)
      .factory(jwtAuthentication::buildBasicAuthModule);
  }
}
