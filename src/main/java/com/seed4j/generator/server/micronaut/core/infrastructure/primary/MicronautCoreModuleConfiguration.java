package com.seed4j.generator.server.micronaut.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MICRONAUT;

import com.seed4j.generator.server.micronaut.core.application.MicronautApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautCoreModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautCoreModule(MicronautApplicationService micronaut) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Micronaut", "Init Micronaut project with dependencies, App, and properties")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(JAVA_BASE).build())
      .tags("server", "micronaut")
      .factory(micronaut::buildModule);
  }
}
