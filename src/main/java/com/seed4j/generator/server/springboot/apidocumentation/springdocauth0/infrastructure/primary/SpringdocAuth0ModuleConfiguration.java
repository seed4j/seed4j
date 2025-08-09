package com.seed4j.generator.server.springboot.apidocumentation.springdocauth0.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.OAUTH_PROVIDER_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRINGDOC_OAUTH_2_AUTH_0;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_OAUTH_2_AUTH_0;

import com.seed4j.generator.server.springboot.apidocumentation.springdocauth0.application.SpringdocAuth0ApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocAuth0ModuleConfiguration {

  @Bean
  SeedModuleResource springdocAuth0Module(SpringdocAuth0ApplicationService springdocAuth0) {
    return SeedModuleResource.builder()
      .slug(SPRINGDOC_OAUTH_2_AUTH_0)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - API Documentation", "Add Auth0 authentication for springdoc")
      .organization(
        SeedModuleOrganization.builder()
          .feature(OAUTH_PROVIDER_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_OAUTH_2_AUTH_0)
          .build()
      )
      .tags("server", "swagger", "springdoc", "auth0")
      .factory(springdocAuth0::buildModule);
  }
}
