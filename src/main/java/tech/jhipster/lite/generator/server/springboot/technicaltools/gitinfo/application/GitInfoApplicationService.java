package tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.domain.GitInfoModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class GitInfoApplicationService {

  private final GitInfoModuleFactory factory;

  public GitInfoApplicationService() {
    this.factory = new GitInfoModuleFactory();
  }

  public JHipsterModule buildGitInfoModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
