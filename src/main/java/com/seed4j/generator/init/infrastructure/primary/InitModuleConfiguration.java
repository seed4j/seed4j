package com.seed4j.generator.init.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.INIT;

import com.seed4j.generator.init.application.InitApplicationService;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InitModuleConfiguration {

  @Bean
  SeedModuleResource initModule(InitApplicationService init) {
    return SeedModuleResource.builder()
      .slug(INIT)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc("Init", "Init project")
      .standalone()
      .tags("server", "init")
      .factory(init::buildModule);
  }

  private SeedModulePropertiesDefinition initPropertiesDefinition() {
    return SeedModulePropertiesDefinition.builder()
      .addProjectBaseName()
      .addProjectName()
      .addEndOfLine()
      .addIndentation()
      .addNodePackageManager()
      .build();
  }
}
