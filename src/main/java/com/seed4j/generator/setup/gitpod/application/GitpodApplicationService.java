package com.seed4j.generator.setup.gitpod.application;

import com.seed4j.generator.setup.gitpod.domain.GitpodModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitpodApplicationService {

  private final GitpodModuleFactory gitpod;

  public GitpodApplicationService() {
    gitpod = new GitpodModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return gitpod.buildModule(properties);
  }
}
