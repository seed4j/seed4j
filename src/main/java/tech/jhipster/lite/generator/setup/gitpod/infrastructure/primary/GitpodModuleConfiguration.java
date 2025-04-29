package tech.jhipster.lite.generator.setup.gitpod.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.GITPOD;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.setup.gitpod.application.GitpodApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class GitpodModuleConfiguration {

  @Bean
  JHipsterModuleResource gitpodModule(GitpodApplicationService gitpod) {
    return JHipsterModuleResource.builder()
      .slug(GITPOD)
      .withoutProperties()
      .apiDoc("Development environment", "Init Gitpod configuration files")
      .standalone()
      .tags("setup", "gitpod")
      .factory(gitpod::buildModule);
  }
}
