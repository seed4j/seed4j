package com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.AUTHENTICATION_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRINGDOC_OAUTH_2;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_OAUTH_2;

import com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.application.SpringdocOauth2ApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocOauth2ModuleConfiguration {

  @Bean
  SeedModuleResource springdocOAuth2Module(SpringdocOauth2ApplicationService springdocOauth2) {
    return SeedModuleResource.builder()
      .slug(SPRINGDOC_OAUTH_2)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - API Documentation", "Add OAuth2 authentication for springdoc")
      .organization(
        SeedModuleOrganization.builder()
          .feature(AUTHENTICATION_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_OAUTH_2)
          .build()
      )
      .tags("server", "swagger", "springdoc")
      .factory(springdocOauth2::buildModule);
  }
}
