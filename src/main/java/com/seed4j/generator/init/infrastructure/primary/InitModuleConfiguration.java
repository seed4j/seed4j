package com.seed4j.generator.init.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.INIT;

import com.seed4j.generator.init.application.InitApplicationService;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InitModuleConfiguration {

  @Bean
  JHipsterModuleResource initModule(InitApplicationService init) {
    return JHipsterModuleResource.builder()
      .slug(INIT)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc("Init", "Init project")
      .standalone()
      .tags("server", "init")
      .factory(init::buildModule);
  }

  private JHipsterModulePropertiesDefinition initPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder()
      .addProjectBaseName()
      .addProjectName()
      .addEndOfLine()
      .addIndentation()
      .addNodePackageManager()
      .build();
  }
}
