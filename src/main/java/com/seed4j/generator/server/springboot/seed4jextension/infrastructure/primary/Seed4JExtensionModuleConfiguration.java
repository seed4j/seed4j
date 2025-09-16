package com.seed4j.generator.server.springboot.seed4jextension.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.*;

import com.seed4j.generator.server.springboot.seed4jextension.application.Seed4JExtensionApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Seed4JExtensionModuleConfiguration {

  @Bean
  Seed4JModuleResource seed4jExtensionModule(Seed4JExtensionApplicationService seed4jextension) {
    return Seed4JModuleResource.builder()
      .slug(SEED4J_EXTENSION)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Seed4J", "Create a Seed4J extension to build custom modules")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server")
      .factory(seed4jextension::buildModule);
  }

  private Seed4JModulePropertiesDefinition propertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder()
      .addBasePackage()
      .addProjectBaseName()
      .addIndentation()
      .addServerPort()
      .addSpringConfigurationFormat()
      .build();
  }
}
