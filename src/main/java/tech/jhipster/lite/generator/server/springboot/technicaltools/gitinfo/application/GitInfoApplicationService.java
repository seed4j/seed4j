package tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.domain.GitInfoModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class GitInfoApplicationService {

  private final GitInfoModuleFactory gitInfo;

  public GitInfoApplicationService() {
    this.gitInfo = new GitInfoModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return gitInfo.buildModule(properties);
  }
}
