package com.seed4j.generator.init.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;

import com.seed4j.generator.init.application.InitApplicationService;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InitModuleConfiguration {

  @Bean
  Seed4JModuleResource initModule(InitApplicationService init) {
    return Seed4JModuleResource.builder()
      .slug(INIT)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc("Init", "Init project")
      .standalone()
      .tags("server", "init")
      .factory(init::buildModule);
  }

  private Seed4JModulePropertiesDefinition initPropertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder()
      .addProjectBaseName()
      .addProjectName()
      .addEndOfLine()
      .addIndentation()
      .addNodePackageManager()
      .build();
  }
}
