package com.seed4j.generator.server.micronaut.openapi.infrastructure.primary;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_OPENAPI;

import com.seed4j.generator.server.micronaut.openapi.application.MicronautOpenApiApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautOpenApiModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautOpenApiModule(MicronautOpenApiApplicationService openApi) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_OPENAPI)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut OpenAPI", "Add OpenAPI/Swagger documentation for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "documentation", "openapi", "swagger")
      .factory(openApi::buildModule);
  }
}
