package com.seed4j.generator.server.micronaut.security.jwt.infrastructure.primary;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_JWT;

import com.seed4j.generator.server.micronaut.security.jwt.application.MicronautSecurityJwtApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautSecurityJwtModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautJwtModule(MicronautSecurityJwtApplicationService jwt) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_JWT)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Security JWT", "Add JWT authentication for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "security", "jwt")
      .factory(jwt::buildModule);
  }
}
