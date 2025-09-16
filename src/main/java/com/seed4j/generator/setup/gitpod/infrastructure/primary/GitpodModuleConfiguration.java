package com.seed4j.generator.setup.gitpod.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITPOD;

import com.seed4j.generator.setup.gitpod.application.GitpodApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GitpodModuleConfiguration {

  @Bean
  Seed4JModuleResource gitpodModule(GitpodApplicationService gitpod) {
    return Seed4JModuleResource.builder()
      .slug(GITPOD)
      .withoutProperties()
      .apiDoc("Development environment", "Init Gitpod configuration files")
      .standalone()
      .tags("setup", "gitpod")
      .factory(gitpod::buildModule);
  }
}
