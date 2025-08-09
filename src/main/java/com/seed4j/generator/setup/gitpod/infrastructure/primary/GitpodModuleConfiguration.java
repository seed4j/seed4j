package com.seed4j.generator.setup.gitpod.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GITPOD;

import com.seed4j.generator.setup.gitpod.application.GitpodApplicationService;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GitpodModuleConfiguration {

  @Bean
  SeedModuleResource gitpodModule(GitpodApplicationService gitpod) {
    return SeedModuleResource.builder()
      .slug(GITPOD)
      .withoutProperties()
      .apiDoc("Development environment", "Init Gitpod configuration files")
      .standalone()
      .tags("setup", "gitpod")
      .factory(gitpod::buildModule);
  }
}
