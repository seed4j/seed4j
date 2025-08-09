package com.seed4j.generator.server.springboot.seed4jextension.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.*;

import com.seed4j.generator.server.springboot.seed4jextension.application.Seed4JExtensionApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Seed4JExtensionModuleConfiguration {

  @Bean
  JHipsterModuleResource seed4jExtensionModule(Seed4JExtensionApplicationService seed4jextension) {
    return JHipsterModuleResource.builder()
      .slug(SEED4J_EXTENSION)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Seed4j", "Create a Seed4J extension to build custom modules")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server")
      .factory(seed4jextension::buildModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder()
      .addBasePackage()
      .addProjectBaseName()
      .addIndentation()
      .addServerPort()
      .addSpringConfigurationFormat()
      .build();
  }
}
