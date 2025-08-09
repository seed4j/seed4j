package com.seed4j.generator.ci.sonarqube.application;

import com.seed4j.generator.ci.sonarqube.domain.SonarQubeModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Component;

@Component
public class SonarQubeApplicationService {

  private final SonarQubeModuleFactory sonarQube;

  public SonarQubeApplicationService(DockerImages dockerImages) {
    sonarQube = new SonarQubeModuleFactory(dockerImages);
  }

  public SeedModule buildBackendModule(SeedModuleProperties properties) {
    return sonarQube.buildBackendModule(properties);
  }

  public SeedModule buildBackendFrontendModule(SeedModuleProperties properties) {
    return sonarQube.buildBackendFrontendModule(properties);
  }

  public SeedModule buildTypescriptModule(SeedModuleProperties properties) {
    return sonarQube.buildTypescriptModule(properties);
  }
}
