package com.seed4j.generator.prettier.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PRETTIER;

import com.seed4j.generator.prettier.application.PrettierApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PrettierModuleConfiguration {

  @Bean
  Seed4JModuleResource prettierModule(PrettierApplicationService prettier) {
    return Seed4JModuleResource.builder()
      .slug(PRETTIER)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc("Prettier", "Format project with prettier")
      .organization(Seed4JModuleOrganization.builder().addDependency(INIT).build())
      .tags("server", "client", "init")
      .factory(prettier::buildModule);
  }

  private Seed4JModulePropertiesDefinition initPropertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addProjectName().addEndOfLine().addIndentation().build();
  }
}
