package com.seed4j.generator.server.springboot.apidocumentation.springdocauth0.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.OAUTH_PROVIDER_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_OAUTH_2_AUTH_0;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2_AUTH_0;

import com.seed4j.generator.server.springboot.apidocumentation.springdocauth0.application.SpringdocAuth0ApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocAuth0ModuleConfiguration {

  @Bean
  Seed4JModuleResource springdocAuth0Module(SpringdocAuth0ApplicationService springdocAuth0) {
    return Seed4JModuleResource.builder()
      .slug(SPRINGDOC_OAUTH_2_AUTH_0)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - API Documentation", "Add Auth0 authentication for springdoc")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(OAUTH_PROVIDER_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_OAUTH_2_AUTH_0)
          .build()
      )
      .tags("server", "swagger", "springdoc", "auth0")
      .factory(springdocAuth0::buildModule);
  }
}
