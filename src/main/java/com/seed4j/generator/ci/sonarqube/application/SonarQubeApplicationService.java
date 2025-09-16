package com.seed4j.generator.ci.sonarqube.application;

import com.seed4j.generator.ci.sonarqube.domain.SonarQubeModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Component;

@Component
public class SonarQubeApplicationService {

  private final SonarQubeModuleFactory sonarQube;

  public SonarQubeApplicationService(DockerImages dockerImages) {
    sonarQube = new SonarQubeModuleFactory(dockerImages);
  }

  public Seed4JModule buildBackendModule(Seed4JModuleProperties properties) {
    return sonarQube.buildBackendModule(properties);
  }

  public Seed4JModule buildBackendFrontendModule(Seed4JModuleProperties properties) {
    return sonarQube.buildBackendFrontendModule(properties);
  }

  public Seed4JModule buildTypescriptModule(Seed4JModuleProperties properties) {
    return sonarQube.buildTypescriptModule(properties);
  }
}
