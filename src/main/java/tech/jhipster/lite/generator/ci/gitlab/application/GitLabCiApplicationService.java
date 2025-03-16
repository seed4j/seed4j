package tech.jhipster.lite.generator.ci.gitlab.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.ci.gitlab.domain.GitLabCiModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class GitLabCiApplicationService {

  private final GitLabCiModuleFactory gitLabCi;

  public GitLabCiApplicationService() {
    gitLabCi = new GitLabCiModuleFactory();
  }

  public JHipsterModule buildGitLabCiMavenModule(JHipsterModuleProperties properties) {
    return gitLabCi.buildGitLabCiMavenModule(properties);
  }

  public JHipsterModule buildGitLabCiGradleModule(JHipsterModuleProperties properties) {
    return gitLabCi.buildGitLabCiGradleModule(properties);
  }
}
