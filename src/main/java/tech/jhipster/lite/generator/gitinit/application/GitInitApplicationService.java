package tech.jhipster.lite.generator.gitinit.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.gitinit.domain.GitInitModuleFactory;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class GitInitApplicationService {

  private GitInitModuleFactory factory;

  public GitInitApplicationService(GitRepository git) {
    factory = new GitInitModuleFactory(git);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
