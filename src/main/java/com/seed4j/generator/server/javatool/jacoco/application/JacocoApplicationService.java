package com.seed4j.generator.server.javatool.jacoco.application;

import com.seed4j.generator.server.javatool.jacoco.domain.JacocoModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Component;

@Component
public class JacocoApplicationService {

  private final JacocoModuleFactory jacoco;

  public JacocoApplicationService() {
    jacoco = new JacocoModuleFactory();
  }

  public JHipsterModule buildJacocoModule(SeedModuleProperties properties) {
    return jacoco.buildJacocoModule(properties);
  }

  public JHipsterModule buildJacocoWithMinCoverageCheckModule(SeedModuleProperties properties) {
    return jacoco.buildJacocoWithMinCoverageCheckModule(properties);
  }
}
