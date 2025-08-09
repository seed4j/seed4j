package com.seed4j.generator.setup.gitpod.application;

import com.seed4j.generator.setup.gitpod.domain.GitpodModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitpodApplicationService {

  private final GitpodModuleFactory gitpod;

  public GitpodApplicationService() {
    gitpod = new GitpodModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return gitpod.buildModule(properties);
  }
}
