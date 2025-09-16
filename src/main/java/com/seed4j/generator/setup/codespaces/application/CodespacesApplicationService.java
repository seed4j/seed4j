package com.seed4j.generator.setup.codespaces.application;

import com.seed4j.generator.setup.codespaces.domain.CodespacesModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CodespacesApplicationService {

  private final CodespacesModuleFactory codespaces;

  public CodespacesApplicationService() {
    codespaces = new CodespacesModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return codespaces.buildModule(properties);
  }
}
