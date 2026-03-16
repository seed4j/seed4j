package com.seed4j.generator.server.micronaut.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MICRONAUT;

import com.seed4j.generator.server.micronaut.security.jwt.application.MicronautSecurityJwtApplicationService;
import com.seed4j.generator.server.micronaut.shared.MicronautModuleSlug;
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
      .slug(new MicronautModuleSlug("micronaut-jwt"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Security JWT", "Add JWT authentication for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "security", "jwt")
      .factory(jwt::buildModule);
  }
}
