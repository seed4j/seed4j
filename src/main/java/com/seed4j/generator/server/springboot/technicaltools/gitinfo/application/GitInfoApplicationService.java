package com.seed4j.generator.server.springboot.technicaltools.gitinfo.application;

import com.seed4j.generator.server.springboot.technicaltools.gitinfo.domain.GitInfoModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GitInfoApplicationService {

  private final GitInfoModuleFactory gitInfo;

  public GitInfoApplicationService() {
    this.gitInfo = new GitInfoModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return gitInfo.buildModule(properties);
  }
}
