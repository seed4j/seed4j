package com.seed4j.generator.setup.codespaces.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITHUB_CODESPACES;

import com.seed4j.generator.setup.codespaces.application.CodespacesApplicationService;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CodespacesModuleConfiguration {

  @Bean
  Seed4JModuleResource codespaceModule(CodespacesApplicationService codespaces) {
    return Seed4JModuleResource.builder()
      .slug(GITHUB_CODESPACES)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc("Development environment", "Init GitHub Codespaces configuration files")
      .standalone()
      .tags("setup", "codespaces")
      .factory(codespaces::buildModule);
  }
}
