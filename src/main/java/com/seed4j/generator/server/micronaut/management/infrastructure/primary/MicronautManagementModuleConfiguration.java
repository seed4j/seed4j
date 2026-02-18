package com.seed4j.generator.server.micronaut.management.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MICRONAUT;

import com.seed4j.generator.server.micronaut.management.application.MicronautManagementApplicationService;
import com.seed4j.generator.server.micronaut.shared.MicronautModuleSlug;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautManagementModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautManagementModule(MicronautManagementApplicationService management) {
    return Seed4JModuleResource.builder()
      .slug(new MicronautModuleSlug("micronaut-management"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Management", "Add management endpoints (health, info, etc.) for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "management", "monitoring")
      .factory(management::buildModule);
  }
}
