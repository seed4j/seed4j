package com.seed4j.generator.server.javatool.jacoco.application;

import com.seed4j.generator.server.javatool.jacoco.domain.JacocoModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Component;

@Component
public class JacocoApplicationService {

  private final JacocoModuleFactory jacoco;

  public JacocoApplicationService() {
    jacoco = new JacocoModuleFactory();
  }

  public Seed4JModule buildJacocoModule(Seed4JModuleProperties properties) {
    return jacoco.buildJacocoModule(properties);
  }

  public Seed4JModule buildJacocoWithMinCoverageCheckModule(Seed4JModuleProperties properties) {
    return jacoco.buildJacocoWithMinCoverageCheckModule(properties);
  }
}
