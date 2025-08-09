package com.seed4j.generator.server.springboot.seed4jextension.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.*;

import com.seed4j.generator.server.springboot.seed4jextension.application.Seed4JExtensionApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Seed4JExtensionModuleConfiguration {

  @Bean
  SeedModuleResource seed4jExtensionModule(Seed4JExtensionApplicationService seed4jextension) {
    return SeedModuleResource.builder()
      .slug(SEED4J_EXTENSION)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Seed4j", "Create a Seed4J extension to build custom modules")
      .organization(SeedModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server")
      .factory(seed4jextension::buildModule);
  }

  private SeedModulePropertiesDefinition propertiesDefinition() {
    return SeedModulePropertiesDefinition.builder()
      .addBasePackage()
      .addProjectBaseName()
      .addIndentation()
      .addServerPort()
      .addSpringConfigurationFormat()
      .build();
  }
}
