package com.seed4j.generator.setup.codespaces.application;

import com.seed4j.generator.setup.codespaces.domain.CodespacesModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CodespacesApplicationService {

  private final CodespacesModuleFactory codespaces;

  public CodespacesApplicationService() {
    codespaces = new CodespacesModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return codespaces.buildModule(properties);
  }
}
