package com.seed4j.generator.setup.gitpod.application;

import com.seed4j.generator.setup.gitpod.domain.GitpodModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitpodApplicationService {

  private final GitpodModuleFactory gitpod;

  public GitpodApplicationService() {
    gitpod = new GitpodModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return gitpod.buildModule(properties);
  }
}
