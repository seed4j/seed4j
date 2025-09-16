package com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.AUTHENTICATION_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_OAUTH_2;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2;

import com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.application.SpringdocOauth2ApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocOauth2ModuleConfiguration {

  @Bean
  Seed4JModuleResource springdocOAuth2Module(SpringdocOauth2ApplicationService springdocOauth2) {
    return Seed4JModuleResource.builder()
      .slug(SPRINGDOC_OAUTH_2)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - API Documentation", "Add OAuth2 authentication for springdoc")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(AUTHENTICATION_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_OAUTH_2)
          .build()
      )
      .tags("server", "swagger", "springdoc")
      .factory(springdocOauth2::buildModule);
  }
}
