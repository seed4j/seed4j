package com.seed4j.generator.setup.codespaces.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GITHUB_CODESPACES;

import com.seed4j.generator.setup.codespaces.application.CodespacesApplicationService;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CodespacesModuleConfiguration {

  @Bean
  SeedModuleResource codespaceModule(CodespacesApplicationService codespaces) {
    return SeedModuleResource.builder()
      .slug(GITHUB_CODESPACES)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc("Development environment", "Init GitHub Codespaces configuration files")
      .standalone()
      .tags("setup", "codespaces")
      .factory(codespaces::buildModule);
  }
}
