package com.seed4j.generator.server.springboot.customjhlite.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CUSTOM_JHLITE;

import com.seed4j.generator.server.springboot.customjhlite.application.CustomJHLiteApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CustomJHLiteModuleConfiguration {

  @Bean
  JHipsterModuleResource customJHLiteModule(CustomJHLiteApplicationService customJHLite) {
    return JHipsterModuleResource.builder()
      .slug(CUSTOM_JHLITE)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Seed4j", "Create a custom JHLite instance to build custom modules")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server")
      .factory(customJHLite::buildModule);
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
