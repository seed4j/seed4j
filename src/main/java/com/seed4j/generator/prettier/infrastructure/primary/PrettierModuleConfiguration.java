package com.seed4j.generator.prettier.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PRETTIER;

import com.seed4j.generator.prettier.application.PrettierApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PrettierModuleConfiguration {

  @Bean
  SeedModuleResource prettierModule(PrettierApplicationService prettier) {
    return SeedModuleResource.builder()
      .slug(PRETTIER)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc("Prettier", "Format project with prettier")
      .organization(SeedModuleOrganization.builder().addDependency(INIT).build())
      .tags("server", "client", "init")
      .factory(prettier::buildModule);
  }

  private SeedModulePropertiesDefinition initPropertiesDefinition() {
    return SeedModulePropertiesDefinition.builder().addProjectBaseName().addProjectName().addEndOfLine().addIndentation().build();
  }
}
