package com.seed4j.generator.server.springboot.apidocumentation.springdocokta.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.OAUTH_PROVIDER_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_OAUTH_2_OKTA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2_OKTA;

import com.seed4j.generator.server.springboot.apidocumentation.springdocokta.application.SpringdocOktaApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocOktaModuleConfiguration {

  @Bean
  Seed4JModuleResource springdocOktaModule(SpringdocOktaApplicationService springdocOkta) {
    return Seed4JModuleResource.builder()
      .slug(SPRINGDOC_OAUTH_2_OKTA)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - API Documentation", "Add Okta authentication for springdoc")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(OAUTH_PROVIDER_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_OAUTH_2_OKTA)
          .build()
      )
      .tags("server", "swagger", "springdoc", "okta")
      .factory(springdocOkta::buildModule);
  }
}
