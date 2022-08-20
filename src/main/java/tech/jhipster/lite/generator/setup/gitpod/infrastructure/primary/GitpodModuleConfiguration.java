package tech.jhipster.lite.generator.setup.gitpod.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.setup.gitpod.application.GitpodApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class GitpodModuleConfiguration {

  @Bean
  JHipsterModuleResource gitpodModule(GitpodApplicationService gitPods) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/developer-tools/gitpod")
      .slug("gitpod")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Gitpod", "Init Gitpod configuration files"))
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("git-init").build())
      .tags("setup")
      .factory(gitPods::buildModule);
  }
}
