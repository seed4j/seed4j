package tech.jhipster.lite.generator.gitinit.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.gitinit.application.GitInitApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class GitInitModuleConfiguration {

  @Bean
  JHipsterModuleResource gitInitModule(GitInitApplicationService gitInits) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/git-init")
      .slug("git-init")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Init", "Git init"))
      .standalone()
      .tags("init")
      .factory(gitInits::buildModule);
  }
}
