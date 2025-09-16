package com.seed4j.generator.server.springboot.technicaltools.gitinfo.application;

import com.seed4j.generator.server.springboot.technicaltools.gitinfo.domain.GitInfoModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitInfoApplicationService {

  private final GitInfoModuleFactory gitInfo;

  public GitInfoApplicationService() {
    this.gitInfo = new GitInfoModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return gitInfo.buildModule(properties);
  }
}
