package com.seed4j.generator.prettier.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PRETTIER;

import com.seed4j.generator.prettier.application.PrettierApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PrettierModuleConfiguration {

  @Bean
  JHipsterModuleResource prettierModule(PrettierApplicationService prettier) {
    return JHipsterModuleResource.builder()
      .slug(PRETTIER)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc("Prettier", "Format project with prettier")
      .organization(JHipsterModuleOrganization.builder().addDependency(INIT).build())
      .tags("server", "client", "init")
      .factory(prettier::buildModule);
  }

  private JHipsterModulePropertiesDefinition initPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addProjectName().addEndOfLine().addIndentation().build();
  }
}
