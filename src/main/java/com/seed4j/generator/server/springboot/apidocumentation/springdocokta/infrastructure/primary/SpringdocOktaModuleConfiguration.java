package com.seed4j.generator.server.springboot.apidocumentation.springdocokta.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.OAUTH_PROVIDER_SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRINGDOC_OAUTH_2_OKTA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_OAUTH_2_OKTA;

import com.seed4j.generator.server.springboot.apidocumentation.springdocokta.application.SpringdocOktaApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocOktaModuleConfiguration {

  @Bean
  JHipsterModuleResource springdocOktaModule(SpringdocOktaApplicationService springdocOkta) {
    return JHipsterModuleResource.builder()
      .slug(SPRINGDOC_OAUTH_2_OKTA)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - API Documentation", "Add Okta authentication for springdoc")
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(OAUTH_PROVIDER_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_OAUTH_2_OKTA)
          .build()
      )
      .tags("server", "swagger", "springdoc", "okta")
      .factory(springdocOkta::buildModule);
  }
}
