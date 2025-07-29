package com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.AUTHENTICATION_SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRINGDOC_OAUTH_2;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_OAUTH_2;

import com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.application.SpringdocOauth2ApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocOauth2ModuleConfiguration {

  @Bean
  JHipsterModuleResource springdocOAuth2Module(SpringdocOauth2ApplicationService springdocOauth2) {
    return JHipsterModuleResource.builder()
      .slug(SPRINGDOC_OAUTH_2)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - API Documentation", "Add OAuth2 authentication for springdoc")
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(AUTHENTICATION_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_OAUTH_2)
          .build()
      )
      .tags("server", "swagger", "springdoc")
      .factory(springdocOauth2::buildModule);
  }
}
